package net.limbomedia.esp.service;

import java.io.IOException;
import java.util.Optional;

import org.kuhlins.binstore.BinStore;
import org.kuhlins.webkit.ex.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.limbomedia.esp.Loggy;
import net.limbomedia.esp.api.DeviceState;
import net.limbomedia.esp.api.Esp8266Request;
import net.limbomedia.esp.api.Esp8266Response;
import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.entity.AppEntity;
import net.limbomedia.esp.entity.DeviceEntity;
import net.limbomedia.esp.entity.VersionEntity;
import net.limbomedia.esp.repo.RepoDevice;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ServiceUpdateImpl implements ServiceUpdate {
  
  @Autowired
  private RepoDevice repoDevice;
  
  @Autowired
  private BinStore binStore;
  
  @Override
  public void esp8266(Esp8266Request req, Esp8266Response res) {
    try {
      ValidatorUpdate.validate(req);
    } catch (ClientException ce) {
      Loggy.UPDATE.info("Invalid ESP8266 request. Source: {}. {}.", req.getSource(), ce.getMessage());
      throw ce;
    }
    
    long now = System.currentTimeMillis();
    
    Optional<DeviceEntity> oDev = repoDevice.findOneByPlatformAndUuid(Platform.ESP8266, req.getMacSta());

    DeviceEntity dev = null;
    if(oDev.isPresent()) {
      dev = oDev.get();
    }
    else {
      dev = new DeviceEntity();
      dev.setSource(req.getSource());
      dev.setState(DeviceState.NEW);
      dev.setPlatform(Platform.ESP8266);
      dev.setTsCreate(now);
      dev.setTsCheck(now);
      dev.setUuid(req.getMacSta());
      dev.setName("");
      dev.setHashFirmware(req.getHashSketch());
      dev = repoDevice.save(dev);
      Loggy.UPDATE.info("New device: {}.", dev);
    }
    
    dev.setTsCheck(now);
    dev.setSource(req.getSource());
    dev.setSizeChip(req.getSizeChip());
    dev.setSizeAppFree(req.getSizeFree());
    dev.setSizeAppUsed(req.getSizeSketch());
    dev.setHashFirmware(req.getHashSketch());
    dev.setInfo(req.getVersion());

    if(!DeviceState.APPROVED.equals(dev.getState())) {
      Loggy.UPDATE.info("Device not approved: {}.", dev);
      res.onNoUpdate();
      return;
    }

    if(!"sketch".equals(req.getMode())) {
      Loggy.UPDATE.info("Spiffs check -> Not supported: {}.", dev);
      res.onNoUpdate();
      return;
    }
    
    AppEntity app = dev.getApp();
    if(app == null) {
      Loggy.UPDATE.info("App check -> None assigned: {}.", dev);
      res.onNoUpdate();
      return;
    }
    
    VersionEntity versionOnDevice = app.getVersionByHash(req.getHashSketch());
    VersionEntity versionLatest = app.getVersionLatest();
    dev.setVersion(versionOnDevice);
    
    if(versionLatest == null) {
      Loggy.UPDATE.info("App check -> No versions: {}.", dev);
      res.onNoUpdate();
      return;
    }
    
    if(versionLatest.equals(versionOnDevice)) {
      Loggy.UPDATE.info("App check -> Already on latest version: {}.", dev);
      res.onNoUpdate();
      return;
    }
    
    dev.setTsUpdate(now);
    Loggy.UPDATE.info("App check -> Sending available update: {}.", dev);
    try {
      binStore.read(versionLatest.getBinId(), res.onUpdate(Mapper.map(versionLatest)));
    } catch (IOException e) {
      Loggy.UPDATE.warn("App check -> Update failed. {}.", e.getMessage());
    }
  }

}
