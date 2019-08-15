package net.limbomedia.esp.service;

import java.io.InputStream;
import java.util.List;

import net.limbomedia.esp.api.ui.App;
import net.limbomedia.esp.api.ui.AppCreate;
import net.limbomedia.esp.api.ui.Device;
import net.limbomedia.esp.api.ui.Version;

public interface ServiceMgmt {
  
  List<Device> devicesGet();
  
  List<App> appsGet();
  App appGet(long appId);
  App appCreate(AppCreate item);
  
  List<Version> versionsGet(long appId);
  void versionCreate(long appId, String filename, InputStream in);
  
}
