package net.limbomedia.esp.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuhlins.webkit.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.limbomedia.esp.api.Esp32Request;
import net.limbomedia.esp.api.Esp8266Request;
import net.limbomedia.esp.service.ServiceUpdate;

@RestController
@RequestMapping("/update")
public class ControllerUpdate {

  @Value("${proxyheader}")
  private String proxyHeader;

  @Autowired
  private ServiceUpdate serviceUpdate;
  
  @GetMapping("/esp8266")
  public void esp8266(HttpServletRequest req, HttpServletResponse res) throws IOException {
    LogUtil.log(req);
    
    Esp8266Request x = new Esp8266Request();
    x.setSource(HttpUtils.remoteAdr(req, proxyHeader));                        // IPv4 IPv6
    x.setUserAgent(req.getHeader("user-agent"));
    x.setMacSta(req.getHeader("x-esp8266-sta-mac"));                           // A0:20:A6:0A:31:B7
    x.setMacAp(req.getHeader("x-esp8266-ap-mac"));                             // A2:20:A6:0A:31:B7
    x.setSdk(req.getHeader("x-esp8266-sdk-version"));                          // 3.0.0-dev(c0f7b44)
    x.setMode(req.getHeader("x-esp8266-mode"));                                // sketch
    x.setVersion(req.getHeader("x-esp8266-version"));                          // some user specifiable version string
    x.setHashSketchMd5(req.getHeader("x-esp8266-sketch-md5"));                 // d3efaf16be4138a42d67ddce27a75506
    x.setSizeChip(parseLong(req.getHeader("x-esp8266-chip-size")));            // 4194304
    x.setSizeSketch(parseLong(req.getHeader("x-esp8266-sketch-size")));        // 337488
    x.setSizeFree(parseLong(req.getHeader("x-esp8266-free-space")));           // 2805760
    
    serviceUpdate.esp8266(x, new EspUpdateHandler(res));
  }
  
  @GetMapping("/esp32")
  public void esp32(HttpServletRequest req, HttpServletResponse res) throws IOException {
    LogUtil.log(req);
    
    Esp32Request x = new Esp32Request();
    x.setSource(HttpUtils.remoteAdr(req, proxyHeader));                      // IPv4 IPv6
    x.setUserAgent(req.getHeader("user-agent"));                             // ESP32-http-Update
    x.setMacSta(req.getHeader("x-esp32-sta-mac"));                           // A0:20:A6:0A:31:B7
    x.setMacAp(req.getHeader("x-esp32-ap-mac"));                             // A2:20:A6:0A:31:B7
    x.setSdk(req.getHeader("x-esp32-sdk-version"));                          // v3.2-18-g977854975
    x.setMode(req.getHeader("x-esp32-mode"));                                // sketch
    x.setVersion(req.getHeader("x-esp32-version"));                          // some user specifiable version string
    x.setHashSketchMd5(req.getHeader("x-esp32-sketch-md5"));                 // d3efaf16be4138a42d67ddce27a75506
    x.setHashSketchSha256(req.getHeader("x-esp32-sketch-sha256"));           // EDA3E03889614DA1D92CABCC374FCC72CA8A46F720198B952ED0BB8CE662D98A
    x.setSizeChip(parseLong(req.getHeader("x-esp32-chip-size")));            // 4194304
    x.setSizeSketch(parseLong(req.getHeader("x-esp32-sketch-size")));        // 337488
    x.setSizeFree(parseLong(req.getHeader("x-esp32-free-space")));           // 2805760
    
    serviceUpdate.esp32(x, new EspUpdateHandler(res));
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
