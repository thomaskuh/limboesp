package net.limbomedia.esp.service;

import net.limbomedia.esp.api.Esp8266Request;
import net.limbomedia.esp.api.Esp8266Response;

public interface ServiceUpdate {
  
  void esp8266(Esp8266Request req, Esp8266Response res);
  
}
