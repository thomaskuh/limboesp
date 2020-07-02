package net.limbomedia.esp.service;

import java.io.IOException;

import org.kuhlins.binstore.BinStore;
import org.kuhlins.webkit.ex.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.limbomedia.esp.Loggy;
import net.limbomedia.esp.api.DeviceState;
import net.limbomedia.esp.api.Esp32Request;
import net.limbomedia.esp.api.Esp8266Request;
import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.api.UpdateHandler;
import net.limbomedia.esp.entity.AppEntity;
import net.limbomedia.esp.entity.DeviceEntity;
import net.limbomedia.esp.entity.ImageDataEntity;
import net.limbomedia.esp.entity.VersionEntity;
import net.limbomedia.esp.repo.RepoDevice;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ServiceUpdateImpl implements ServiceUpdate {
  
  @Autowired
  private RepoDevice repoDevice;
  
  @Autowired
  private BinStore binStore;
  
  private DeviceEntity getCreateDevice(Platform platform, String uuid, String source, String fwHash) {
    return repoDevice.findOneByPlatformAndUuid(platform, uuid).orElseGet(() -> {
      long now = System.currentTimeMillis();
      DeviceEntity dev = new DeviceEntity();
      dev.setSource(source);
      dev.setState(DeviceState.NEW);
      dev.setPlatform(platform);
      dev.setTsCreate(now);
      dev.setTsCheck(now);
      dev.setUuid(uuid);
      dev.setName("");
      // TODO: maybe make nullable
      dev.setHashFirmware(fwHash);
      dev = repoDevice.save(dev);
      Loggy.UPDATE.info("New device: {}.", dev);
      return dev;
    });
  }
  
  private void checkDeliverUpdateApp(DeviceEntity dev, UpdateHandler updateHandler) {
    if(!DeviceState.APPROVED.equals(dev.getState())) {
      Loggy.UPDATE.info("Device not approved: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }

    AppEntity app = dev.getApp();
    if(app == null) {
      Loggy.UPDATE.info("App check -> None assigned: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }

    VersionEntity versionOnDevice = app.getVersionByHash(dev.getHashFirmware());
    VersionEntity versionLatest = app.getVersionLatest();
    dev.setVersion(versionOnDevice);
    
    if(versionLatest == null) {
      Loggy.UPDATE.info("App check -> No versions: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }
    
    if(versionLatest.equals(versionOnDevice)) {
      Loggy.UPDATE.info("App check -> Already on latest version: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }
    
    dev.setTsUpdate(System.currentTimeMillis());
    Loggy.UPDATE.info("App check -> Sending available update: {}.", dev);
    try {
      binStore.readStream(versionLatest.getBinId(), updateHandler.onUpdate(Mapper.map(versionLatest)));
    } catch (IOException e) {
      Loggy.UPDATE.warn("App check -> Update failed. {}.", e.getMessage());
    }    
  }
  
  private void checkDeliverUpdateData(DeviceEntity dev, UpdateHandler updateHandler) {
    if(!DeviceState.APPROVED.equals(dev.getState())) {
      Loggy.UPDATE.info("Device not approved: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }
    
    ImageDataEntity imageData = dev.getImageData();
    
    if(imageData == null) {
      Loggy.UPDATE.info("Data check -> No image: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }

    if(imageData.getTsFetch() != 0) {
      Loggy.UPDATE.info("Data check -> Already on latest image: {}.", dev);
      updateHandler.onNoUpdate();
      return;
    }
    
    Loggy.UPDATE.info("Data check -> Sending available update: {}.", dev);
    try {
      binStore.readStream(imageData.getBinId(), updateHandler.onUpdate(Mapper.map(imageData)));
      imageData.setTsFetch(System.currentTimeMillis());
    } catch (IOException e) {
      Loggy.UPDATE.warn("Data check -> Update failed. {}.", e.getMessage());
    }
  }
  
  @Override
  public void esp8266(Esp8266Request req, UpdateHandler updateHandler) {
    try {
      ValidatorUpdate.validate(req);
    } catch (ClientException ce) {
      Loggy.UPDATE.info("Invalid ESP8266 request. Source: {}. {}.", req.getSource(), ce.getMessage());
      throw ce;
    }
    
    DeviceEntity dev = getCreateDevice(Platform.ESP8266, req.getMacSta(), req.getSource(), req.getHashSketchMd5());

    dev.setTsCheck(System.currentTimeMillis());
    dev.setSource(req.getSource());
    dev.setSizeChip(req.getSizeChip());
    dev.setSizeAppFree(req.getSizeFree());
    dev.setSizeAppUsed(req.getSizeSketch());
    dev.setHashFirmware(req.getHashSketchMd5());
    dev.setInfo(req.getVersion());

    if("spiffs".equals(req.getMode())) {
      checkDeliverUpdateData(dev, updateHandler);
    }
    else {
      checkDeliverUpdateApp(dev, updateHandler);
    }
  }

  @Override
  public void esp32(Esp32Request req, UpdateHandler updateHandler) {
    try {
      ValidatorUpdate.validate(req);
    } catch (ClientException ce) {
      Loggy.UPDATE.info("Invalid ESP32 request. Source: {}. {}.", req.getSource(), ce.getMessage());
      throw ce;
    }

    DeviceEntity dev = getCreateDevice(Platform.ESP32, req.getMacSta(), req.getSource(), req.getHashSketchMd5());

    long now = System.currentTimeMillis();
    dev.setTsCheck(now);
    dev.setSource(req.getSource());
    dev.setSizeChip(req.getSizeChip());
    dev.setSizeAppFree(req.getSizeFree());
    dev.setSizeAppUsed(req.getSizeSketch());
    dev.setHashFirmware(req.getHashSketchMd5());
    dev.setInfo(req.getVersion());
    
    if("spiffs".equals(req.getMode())) {
      checkDeliverUpdateData(dev, updateHandler);
    }
    else {
      checkDeliverUpdateApp(dev, updateHandler);
    }    
  }

}
