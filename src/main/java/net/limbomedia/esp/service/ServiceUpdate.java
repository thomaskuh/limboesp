package net.limbomedia.esp.service;

import net.limbomedia.esp.api.update.UpdateRequestEsp8266;

public interface ServiceUpdate {
  
  void handleUpdateEsp8266(UpdateRequestEsp8266 req);
  
}
