package net.limbomedia.esp.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.api.update.UpdateRequestEsp8266;
import net.limbomedia.esp.entity.DeviceEntity;
import net.limbomedia.esp.repo.RepoDevice;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ServiceUpdateImpl implements ServiceUpdate {
  
  private static final Logger L = LoggerFactory.getLogger(ServiceUpdateImpl.class);
  
  @Autowired
  private RepoDevice repoDevice;
  
  @Override
  public void handleUpdateEsp8266(UpdateRequestEsp8266 req) {
    L.info("Handling: {}.", req);
    
    Optional<DeviceEntity> oDeviceEntity = repoDevice.findOneByPlatformAndUuid(Platform.ESP8266, req.getMacSta());
    
    long now = System.currentTimeMillis();
    
    DeviceEntity deviceEntity = null;
    if(oDeviceEntity.isPresent()) {
      deviceEntity = oDeviceEntity.get();
      L.info("Existing device detected: {}", deviceEntity);
      deviceEntity.setTsCheck(now);
    }
    else {
      deviceEntity = new DeviceEntity();
      deviceEntity.setPlatform(Platform.ESP8266);
      deviceEntity.setTsCreate(now);
      deviceEntity.setTsCheck(now);
      deviceEntity.setUuid(req.getMacSta());
      deviceEntity.setName("");
      deviceEntity.setHashFirmware(req.getHashSketch());
      deviceEntity = repoDevice.save(deviceEntity);
      L.info("New device detected. Added to database: {}.", deviceEntity);
    }
    
  }

}
