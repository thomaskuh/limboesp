package net.limbomedia.esp.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.api.update.UpdateRequestEsp8266;
import net.limbomedia.esp.service.ServiceUpdate;

@RestController
@RequestMapping("/update")
public class ControllerUpdate {
  
  private static final Logger L = LoggerFactory.getLogger(ControllerUpdate.class);
  
  @Autowired
  private ServiceUpdate serviceUpdate;
  
  @GetMapping("/generic")
  public void esp8266(HttpServletRequest req, HttpServletResponse res) throws IOException {
    // L.info("=== Incoming update request: {} ===", req.getPathInfo());
    /*
    Enumeration<String> headerNames = req.getHeaderNames();
    while(headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      L.info("Header: {} -> {}.", headerName, req.getHeader(headerName));
    }
    */
    
    Platform platform = identifyPlatform(req);
    if(platform == null) {
      L.warn("Unable to idenfity device. Unknown platform or invalid request.");
      res.sendError(HttpStatus.BAD_REQUEST.value());
      return;
    }
    
    UpdateRequestEsp8266 updateRequest = parseRequestEsp8266(req);
    serviceUpdate.handleUpdateEsp8266(updateRequest);
    
    res.sendError(500);
  }
  
  private Platform identifyPlatform(HttpServletRequest req) {
    if("ESP8266-http-Update".equals(req.getHeader("user-agent"))) {
      return Platform.ESP8266;
    }
    return null;
  }
  
  private UpdateRequestEsp8266 parseRequestEsp8266(HttpServletRequest req) {
    UpdateRequestEsp8266 result = new UpdateRequestEsp8266();
    
    result.setMacSta(req.getHeader("x-esp8266-sta-mac"));                           // A0:20:A6:0A:31:B7
    result.setMacAp(req.getHeader("x-esp8266-ap-mac"));                             // A2:20:A6:0A:31:B7
    result.setSizeChip(Long.parseLong(req.getHeader("x-esp8266-chip-size")));       // 4194304
    result.setSizeSketch(Long.parseLong(req.getHeader("x-esp8266-sketch-size")));   //  337488
    result.setSizeFree(Long.parseLong(req.getHeader("x-esp8266-free-space")));      // 2805760
    result.setHashSketch(req.getHeader("x-esp8266-sketch-md5"));                    // d3efaf16be4138a42d67ddce27a75506
    
    // Not in use right now
    // x-esp8266-sdk-version -> 3.0.0-dev(c0f7b44)
    // x-esp8266-mode -> sketch
    // x-esp8266-version -> some user specifiable version string
    
    return result;
  }
  
}
