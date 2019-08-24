package net.limbomedia.esp.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuhlins.webkit.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.limbomedia.esp.api.Esp8266Request;
import net.limbomedia.esp.service.ServiceUpdate;

@RestController
@RequestMapping("/update")
public class ControllerUpdate {

  @Autowired
  private ServiceUpdate serviceUpdate;

  
  @GetMapping("/esp8266")
  public void esp8266(HttpServletRequest req, HttpServletResponse res) throws IOException {
    LogUtil.log(req);
    Esp8266Request updateRequest = parseRequestEsp8266(req);
    serviceUpdate.esp8266(updateRequest, new Esp8266HttpResponse(res));
    

  }
  
  private Esp8266Request parseRequestEsp8266(HttpServletRequest req) {
    Esp8266Request result = new Esp8266Request();
    
    result.setSource(HttpUtils.remoteAdr(req, null));                               // IPv4 IPv6
    result.setMacSta(req.getHeader("x-esp8266-sta-mac"));                           // A0:20:A6:0A:31:B7
    result.setMacAp(req.getHeader("x-esp8266-ap-mac"));                             // A2:20:A6:0A:31:B7
    result.setSdk(req.getHeader("x-esp8266-sdk-version"));                          // 3.0.0-dev(c0f7b44)
    result.setMode(req.getHeader("x-esp8266-mode"));                                // sketch
    result.setVersion(req.getHeader("x-esp8266-version"));                          // some user specifiable version string
    result.setHashSketch(req.getHeader("x-esp8266-sketch-md5"));                    // d3efaf16be4138a42d67ddce27a75506
    result.setSizeChip(parseLong(req.getHeader("x-esp8266-chip-size")));            // 4194304
    result.setSizeSketch(parseLong(req.getHeader("x-esp8266-sketch-size")));        // 337488
    result.setSizeFree(parseLong(req.getHeader("x-esp8266-free-space")));           // 2805760
    
    return result;
  }
  
  private Long parseLong(String value) {
    Long result = null;
    try {
      result = Long.parseLong(value);
    } catch(NullPointerException | NumberFormatException e) {
      /* dont care */
    }
    return result;
  }
  
}
