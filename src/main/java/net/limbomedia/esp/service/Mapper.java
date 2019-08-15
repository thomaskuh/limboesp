package net.limbomedia.esp.service;

import net.limbomedia.esp.api.ui.App;
import net.limbomedia.esp.api.ui.Device;
import net.limbomedia.esp.api.ui.Version;
import net.limbomedia.esp.entity.AppEntity;
import net.limbomedia.esp.entity.DeviceEntity;
import net.limbomedia.esp.entity.VersionEntity;

public class Mapper {
  
  public static Device map(DeviceEntity from) {
    Device to = new Device();
    to.setId(from.getId());
    to.setUuid(from.getUuid());
    to.setName(from.getName());
    to.setPlatform(from.getPlatform());
    to.setTsCheck(from.getTsCheck());
    to.setTsCreate(from.getTsCreate());
    to.setTsUpdate(from.getTsUpdate());
    return to;
  }
  
  public static App map(AppEntity from) {
    App to = new App();
    to.setId(from.getId());
    to.setName(from.getName());
    to.setPlatform(from.getPlatform());
    return to;
  }
  
  public static Version map(VersionEntity from) {
    Version to = new Version();
    to.setId(from.getId());
    to.setNr(from.getNr());
    to.setName(from.getName());
    to.setBinId(from.getBinId());
    to.setBinSize(from.getBinSize());
    to.setBinHash(from.getBinHash());
    return to;
  }
  
}
