package net.limbomedia.esp.x.mgmt;

import com.google.common.base.Objects;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.limbomedia.esp.db.AppEntity;
import net.limbomedia.esp.db.DeviceEntity;
import net.limbomedia.esp.db.ImageDataEntity;
import net.limbomedia.esp.db.RepoApp;
import net.limbomedia.esp.db.RepoDevice;
import net.limbomedia.esp.db.RepoImageData;
import net.limbomedia.esp.db.RepoVersion;
import net.limbomedia.esp.db.VersionEntity;
import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.common.api.Version;
import net.limbomedia.esp.x.mgmt.api.App;
import net.limbomedia.esp.x.mgmt.api.AppCreate;
import net.limbomedia.esp.x.mgmt.api.AppUpdate;
import net.limbomedia.esp.x.mgmt.api.Device;
import net.limbomedia.esp.x.mgmt.api.DeviceUpdate;
import net.limbomedia.esp.x.mgmt.platform.PlatformHandler;
import org.kuhlins.lib.binstore.BinStore;
import org.kuhlins.lib.webkit.ex.NotFoundException;
import org.kuhlins.lib.webkit.ex.SystemException;
import org.kuhlins.lib.webkit.ex.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceMgmtImpl implements ServiceMgmt {

    @Autowired
    private RepoDevice repoDevice;

    @Autowired
    private RepoApp repoApp;

    @Autowired
    private RepoVersion repoVersion;

    @Autowired
    private RepoImageData repoImageData;

    @Autowired
    private BinStore binStore;

    @Autowired
    private Collection<PlatformHandler> platformHandlers;

    private PlatformHandler getHandler(Platform platform) {
        return platformHandlers.stream()
                .filter(ph -> ph.getPlatform() == platform)
                .findFirst()
                .orElseThrow(() -> new SystemException("No handler found for platform: " + platform, null));
    }

    @Override
    public List<App> appsGet(Platform filterPlatform) {
        Stream<AppEntity> stream = repoApp.findAll().stream();

        if (filterPlatform != null) {
            stream = stream.filter(x -> x.getPlatform().equals(filterPlatform));
        }

        return stream.map(Mapper::map).collect(Collectors.toList());
    }

    @Override
    public App appCreate(AppCreate item) {
        ValidatorMgmt.validate(item, repoApp);

        AppEntity app = new AppEntity();
        app.setName(item.getName());
        app.setPlatform(item.getPlatform());
        app = repoApp.save(app);

        return Mapper.map(app);
    }

    @Override
    public App appUpdate(long appId, AppUpdate body) {
        AppEntity result = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());

        Normalizer.normalize(body);
        ValidatorMgmt.validate(body, result, repoApp);
        result.setName(body.getName());
        result.setKey(body.getKey());
        return Mapper.map(result);
    }

    @Override
    public void appDelete(long appId) {
        AppEntity app = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());

        repoDevice.findByApp(app).forEach(device -> {
            device.setApp(null);
            device.setVersion(null);
        });

        new HashSet<>(app.getVersions()).forEach(version -> {
            version.setApp(null);
            binStore.deleteQuiet(version.getBinId());
            repoVersion.delete(version);
        });

        repoApp.delete(app);
    }

    @Override
    public App appGet(long appId) {
        AppEntity result = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());
        return Mapper.map(result);
    }

    @Override
    public void versionCreate(long appId, String filename, InputStream is) {
        AppEntity app = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());

        ValidatorMgmt.validateVersionCreate1(filename, is);

        long nr = app.nextVersion();
        String binId = null;
        long binSize = 0;
        String binHash = null;

        try {
            binId = binStore.write(is);
            binSize = binStore.size(binId);
            long binSizeFinal = binSize;
            binHash = binStore.readStream(
                    binId, stream -> getHandler(app.getPlatform()).hashApp(binSizeFinal, stream));
        } catch (IOException e) {
            throw new SystemException(e);
        }

        try {
            ValidatorMgmt.validateVersionCreate2(app, binSize, binHash);
        } catch (ValidationException ve) {
            try {
                binStore.delete(binId);
            } catch (IOException e) {
                /* dont care if single files gets lost */ }
            throw ve;
        }

        VersionEntity version = new VersionEntity();
        version.setApp(app);
        version.setName(filename);
        version.setNr(nr);
        version.setTs(System.currentTimeMillis());
        version.setBinId(binId);
        version.setBinSize(binSize);
        version.setBinHash(binHash);
        version = repoVersion.save(version);
    }

    @Override
    public void deviceImageDataCreate(long deviceId, String filename, InputStream is) {
        DeviceEntity device = repoDevice.findById(deviceId).orElseThrow(() -> new NotFoundException());

        ValidatorMgmt.validateVersionCreate1(filename, is);

        String binId = null;
        long binSize = 0;
        String binHash = null;

        try {
            binId = binStore.write(is);
            binSize = binStore.size(binId);
            long binSizeFinal = binSize;
            binHash = binStore.readStream(
                    binId, stream -> getHandler(device.getPlatform()).hashData(binSizeFinal, stream));
        } catch (IOException e) {
            throw new SystemException(e);
        }

        try {
            ValidatorMgmt.validateDeviceImageData(binSize, binHash);
        } catch (ValidationException ve) {
            try {
                binStore.delete(binId);
            } catch (IOException e) {
                /* dont care if single files gets lost */ }
            throw ve;
        }

        ImageDataEntity imageData = device.getImageData();
        if (imageData == null) {
            imageData = new ImageDataEntity();
            imageData.setDevice(device);
            imageData.setName(filename);
            imageData.setTs(System.currentTimeMillis());
            imageData.setBinId(binId);
            imageData.setBinSize(binSize);
            imageData.setBinHash(binHash);
            imageData = repoImageData.save(imageData);
        } else {
            try {
                binStore.delete(imageData.getBinId());
            } catch (IOException e) {
                /* dont care if single files gets lost */ }

            imageData.setName(filename);
            imageData.setTs(System.currentTimeMillis());
            imageData.setTsFetch(0);
            imageData.setBinId(binId);
            imageData.setBinSize(binSize);
            imageData.setBinHash(binHash);
        }
    }

    @Override
    public List<Version> versionsGet(long appId) {
        AppEntity app = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());
        return app.getVersions().stream().map(Mapper::map).collect(Collectors.toList());
    }

    public List<Device> devicesGet() {
        return repoDevice.findAll().stream().map(Mapper::map).collect(Collectors.toList());
    }

    @Override
    public Device deviceGet(long deviceId) {
        DeviceEntity dev = repoDevice.findById(deviceId).orElseThrow(() -> new NotFoundException());
        return Mapper.map(dev);
    }

    @Override
    public Device deviceUpdate(long deviceId, DeviceUpdate body) {
        DeviceEntity dev = repoDevice.findById(deviceId).orElseThrow(() -> new NotFoundException());

        Normalizer.normalize(body);
        ValidatorMgmt.validate(body);

        AppEntity app = body.getAppId() == null
                ? null
                : repoApp.findById(body.getAppId()).orElseThrow(() -> new NotFoundException());

        dev.setName(body.getName());
        dev.setSecret(body.getSecret());
        dev.setState(body.getState());

        if (!Objects.equal(dev.getApp(), app)) {
            dev.setApp(app);
            dev.setVersion(null);
        }

        return Mapper.map(dev);
    }

    @Override
    public void deviceDelete(long deviceId) {
        DeviceEntity dev = repoDevice.findById(deviceId).orElseThrow(() -> new NotFoundException());

        ImageDataEntity ide = dev.getImageData();
        if (ide != null) {
            binStore.deleteQuiet(ide.getBinId());
            repoImageData.delete(ide);
        }

        repoDevice.delete(dev);
    }
}
