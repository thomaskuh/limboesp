package net.limbomedia.esp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.kuhlins.binstore.BinStore;
import org.kuhlins.webkit.ex.NotFoundException;
import org.kuhlins.webkit.ex.SystemException;
import org.kuhlins.webkit.ex.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Funnels;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;

import net.limbomedia.esp.api.ui.App;
import net.limbomedia.esp.api.ui.AppCreate;
import net.limbomedia.esp.api.ui.Device;
import net.limbomedia.esp.api.ui.Version;
import net.limbomedia.esp.entity.AppEntity;
import net.limbomedia.esp.entity.VersionEntity;
import net.limbomedia.esp.repo.RepoApp;
import net.limbomedia.esp.repo.RepoDevice;
import net.limbomedia.esp.repo.RepoVersion;

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
  private BinStore binStore;
  
  private HashFunction hfMd5 = Hashing.md5();
  
  public List<Device> devicesGet() {
    return repoDevice.findAll().stream().map(Mapper::map).collect(Collectors.toList());
  }

  @Override
  public List<App> appsGet() {
    return repoApp.findAll().stream().map(Mapper::map).collect(Collectors.toList());
  }

  @Override
  public App appCreate(AppCreate item) {
    ValidatorApp.validateCreate(item, repoApp);
    
    AppEntity app = new AppEntity();
    app.setName(item.getName());
    app.setPlatform(item.getPlatform());
    app = repoApp.save(app);
    
    return Mapper.map(app);
  }

  @Override
  public App appGet(long appId) {
    AppEntity result = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());
    return Mapper.map(result);
  }

  @Override
  public void versionCreate(long appId, String filename, InputStream is) {
    AppEntity app = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());
    
    ValidatorApp.validateVersionCreate(filename, is);
    
    long nr = app.nextVersion();
    String binId = null;
    long binSize = 0;
    String binHash = null;

    try {
      binId = binStore.write(is);
      binSize = binStore.size(binId);
      binHash = binStore.read(binId, stream -> {
        Hasher hasher = hfMd5.newHasher();
        ByteStreams.copy(stream, Funnels.asOutputStream(hasher));
        return hasher.hash().toString();
      });
    } catch (IOException e) {
      throw new SystemException(e);
    }
    System.out.println("Hash: " + binHash);
    
    try {
      ValidatorApp.validateVersionCreate2(app, binSize, binHash);
    } catch(ValidationException ve) {
      try {
        binStore.delete(binId);
      } catch (IOException e) {/* dont care if single files get lost */}
      throw ve;
    }
    
    VersionEntity version = new VersionEntity();
    version.setApp(app);
    version.setName(filename);
    version.setNr(nr);
    version.setBinId(binId);
    version.setBinSize(binSize);
    version.setBinHash(binHash);
    version = repoVersion.save(version);
  }

  @Override
  public List<Version> versionsGet(long appId) {
    AppEntity app = repoApp.findById(appId).orElseThrow(() -> new NotFoundException());
    return app.getVersions().stream().map(Mapper::map).collect(Collectors.toList());
  }

  
}
