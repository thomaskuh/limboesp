package net.limbomedia.esp.x.update;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import net.limbomedia.esp.Loggy;
import net.limbomedia.esp.db.AppEntity;
import net.limbomedia.esp.db.DeviceEntity;
import net.limbomedia.esp.db.ImageDataEntity;
import net.limbomedia.esp.db.RepoApp;
import net.limbomedia.esp.db.RepoDevice;
import net.limbomedia.esp.db.VersionEntity;
import net.limbomedia.esp.x.common.api.DeviceState;
import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.mgmt.Mapper;
import net.limbomedia.esp.x.update.api.Errors;
import net.limbomedia.esp.x.update.api.UpdateRequest;
import net.limbomedia.esp.x.update.api.What;
import net.limbomedia.esp.x.update.proto.Responder;
import org.kuhlins.lib.binstore.BinStore;
import org.kuhlins.lib.binstore.IoFunction;
import org.kuhlins.lib.webkit.ex.NotFoundException;
import org.kuhlins.lib.webkit.ex.ValidationException;
import org.kuhlins.lib.webkit.ex.model.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceUpdateImpl implements ServiceUpdate {

    @Autowired
    private RepoDevice repoDevice;

    @Autowired
    private RepoApp repoApp;

    @Autowired
    private BinStore binStore;

    @Override
    public Optional<Platform> getAppPlatform(String appKey) {
        return repoApp.findOneByKey(appKey).map(AppEntity::getPlatform);
    }

    private void checkDeliverUpdateApp(DeviceEntity dev, Responder responder) {
        AppEntity app = dev.getApp();
        if (app == null) {
            Loggy.UPDATE_APP.info("Update App NO -> No app assigned. {}.", dev);
            responder.onNoUpdate();
            return;
        }

        VersionEntity versionOnDevice = app.getVersionByHash(dev.getHashFirmware());
        VersionEntity versionLatest = app.getVersionLatest();
        dev.setVersion(versionOnDevice);

        if (versionLatest == null) {
            Loggy.UPDATE_APP.info("Update App NO -> No versions available. {}.", dev);
            responder.onNoUpdate();
            return;
        }

        if (versionLatest.equals(versionOnDevice)) {
            Loggy.UPDATE_APP.info("Update App NO -> Already up-to-date. {}.", dev);
            responder.onNoUpdate();
            return;
        }

        IoFunction<InputStream, Void> onData = responder.onData(Mapper.map(versionLatest));
        if (onData == null) {
            Loggy.UPDATE_APP.info("Update App YES -> Announcing. {}.", dev);
        } else {
            Loggy.UPDATE_APP.info("Update App YES -> Sending. {}.", dev);
            try {
                binStore.readStream(versionLatest.getBinId(), onData);
                dev.setTsUpdate(System.currentTimeMillis());
            } catch (IOException e) {
                Loggy.UPDATE_APP.warn("Update App FAIL -> {}. {}.", e.getMessage(), dev);
            }
        }
    }

    private void checkDeliverUpdateData(DeviceEntity dev, Responder responder) {
        ImageDataEntity imageData = dev.getImageData();

        if (imageData == null) {
            Loggy.UPDATE_APP.info("Update Data NO -> No image. {}.", dev);
            responder.onNoUpdate();
            return;
        }

        if (imageData.getTsFetch() != 0) {
            Loggy.UPDATE_APP.info("Update Data NO -> Already up-to-date. {}.", dev);
            responder.onNoUpdate();
            return;
        }

        IoFunction<InputStream, Void> onData = responder.onData(Mapper.map(imageData));
        if (onData == null) {
            Loggy.UPDATE_APP.info("Update Data YES -> Announcing. {}.", dev);
        } else {
            Loggy.UPDATE_APP.info("Update Data YES -> Sending. {}.", dev);
            try {
                binStore.readStream(imageData.getBinId(), onData);
                imageData.setTsFetch(System.currentTimeMillis());
            } catch (IOException e) {
                Loggy.UPDATE_APP.warn("Update Data FAIL -> {}. {}.", e.getMessage(), dev);
            }
        }
    }

    private DeviceEntity getCreateUpdateDevice(UpdateRequest req) throws ValidationException {
        Normalizer.normalize(req);
        ValidatorUpdate.validateForDevice(req);

        Optional<DeviceEntity> odev = repoDevice.findOneByUuid(req.getUuid());

        DeviceEntity dev;
        if (odev.isPresent()) {
            dev = odev.get();
            ValidationException ve = new ValidationException();

            if (dev.getPlatform() != req.getPlatform()) {
                ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_PLATFORM_MISMATCH.name()));
            }
            if (dev.getSecret() != null && !dev.getSecret().equals(req.getSecret())) {
                ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_SECRET_MISMATCH.name()));
            }
            ve.throwOnDetails();
        } else {
            long now = System.currentTimeMillis();
            dev = new DeviceEntity();
            dev.setUuid(req.getUuid());
            dev.setSource(req.getSource());
            dev.setState(DeviceState.NEW);
            dev.setPlatform(req.getPlatform());
            dev.setTsCreate(now);
            dev.setTsCheck(now);
            dev.setSecret(req.getSecret());
            dev = repoDevice.save(dev);
            Loggy.UPDATE_DEV.info("New device: {}, {}.", dev, req);
        }

        long now = System.currentTimeMillis();
        dev.setTsCheck(now);
        dev.setSource(req.getSource());
        dev.setHashFirmware(req.getHash());
        dev.setInfo(req.getInfo());
        return dev;
    }

    @Override
    public void updateDevice(UpdateRequest req, Responder responder) {
        DeviceEntity dev = null;
        try {
            dev = getCreateUpdateDevice(req);
        } catch (ValidationException e) {
            Loggy.UPDATE_DEV.info("Invalid request. {}, {}.", req, e.getMessage());
            throw e;
        }

        if (!DeviceState.APPROVED.equals(dev.getState())) {
            Loggy.UPDATE_DEV.info("Update NO -> Device not approved. {}.", dev);
            responder.onNoUpdate();
            return;
        }

        if (What.DATA == req.getWhat()) {
            checkDeliverUpdateData(dev, responder);
        } else {
            checkDeliverUpdateApp(dev, responder);
        }
    }

    @Override
    public void updateApp(String appKey, UpdateRequest req, Responder responder) {
        try {
            Normalizer.normalize(req);
            ValidatorUpdate.validateForApp(req);
        } catch (ValidationException e) {
            Loggy.UPDATE_APP.info("Invalid request. App: {}, {}, {}.", appKey, req, e.getMessage());
            throw e;
        }

        AppEntity app = repoApp.findOneByKey(appKey).orElseThrow(() -> new NotFoundException());

        VersionEntity versionLatest = app.getVersionLatest();
        if (versionLatest == null) {
            Loggy.UPDATE_APP.info("Update App NO -> No versions available. App: {}, {}.", appKey, req);
            responder.onNoUpdate();
            return;
        }
        if (versionLatest.getBinHash().equals(req.getHash())) {
            Loggy.UPDATE_APP.info("Update App NO -> Already up-to-date. App: {}, {}.", appKey, req);
            responder.onNoUpdate();
            return;
        }

        IoFunction<InputStream, Void> onData = responder.onData(Mapper.map(versionLatest));
        if (onData == null) {
            Loggy.UPDATE_APP.info("Update App YES -> Announcing. App: {}, {}.", appKey, req);
        } else {
            Loggy.UPDATE_APP.info("Update App YES -> Sending. App: {}, {}.", appKey, req);
            try {
                binStore.readStream(versionLatest.getBinId(), onData);
            } catch (IOException e) {
                Loggy.UPDATE_APP.warn("Update App FAIL -> {}. App: {}, {}.", e.getMessage(), appKey, req);
            }
        }
    }
}
