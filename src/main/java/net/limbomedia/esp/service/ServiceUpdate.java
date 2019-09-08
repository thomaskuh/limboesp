package net.limbomedia.esp.service;

import net.limbomedia.esp.api.Esp32Request;
import net.limbomedia.esp.api.Esp8266Request;
import net.limbomedia.esp.api.UpdateHandler;

public interface ServiceUpdate {
  
  void esp8266(Esp8266Request req, UpdateHandler updateHandler);
  
  void esp32(Esp32Request req, UpdateHandler updateHandler);
  
}
