package net.limbomedia.esp.service;

import java.io.InputStream;
import java.util.List;

import net.limbomedia.esp.api.App;
import net.limbomedia.esp.api.AppCreate;
import net.limbomedia.esp.api.AppUpdate;
import net.limbomedia.esp.api.Device;
import net.limbomedia.esp.api.DeviceUpdate;
import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.api.Version;

public interface ServiceMgmt {
  
  List<Device> devicesGet();
  Device deviceGet(long deviceId);
  Device deviceUpdate(long deviceId, DeviceUpdate body);
  void deviceImageDataCreate(long deviceId, String filename, InputStream in);
  
  List<App> appsGet(Platform filterPlatform);
  App appGet(long appId);
  App appCreate(AppCreate item);
  App appUpdate(long appId, AppUpdate body);
  
  List<Version> versionsGet(long appId);
  void versionCreate(long appId, String filename, InputStream in);
  
}
