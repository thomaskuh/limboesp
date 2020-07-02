package net.limbomedia.esp.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuhlins.webkit.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.limbomedia.esp.api.App;
import net.limbomedia.esp.api.AppCreate;
import net.limbomedia.esp.api.AppUpdate;
import net.limbomedia.esp.api.Device;
import net.limbomedia.esp.api.DeviceUpdate;
import net.limbomedia.esp.api.Platform;
import net.limbomedia.esp.api.Version;
import net.limbomedia.esp.service.ServiceMgmt;

@RestController
@RequestMapping("/api")
@Secured
public class ControllerApi {
   
  @Autowired
  private ServiceMgmt serviceMgmt;
  
  
  @GetMapping("/probe")
  public void probe() {
  }  
  
  @GetMapping("/device")
  public List<Device> devicesGet() {
    return serviceMgmt.devicesGet();
  }
  
  @GetMapping("/device/{deviceId}")
  public Device deviceGet(@PathVariable long deviceId) {
    return serviceMgmt.deviceGet(deviceId);
  }
  
  @PostMapping("/device/{deviceId}")
  public Device deviceUpdate(@PathVariable long deviceId, @RequestBody DeviceUpdate body) {
    return serviceMgmt.deviceUpdate(deviceId, body);
  }
  
  @PostMapping("/device/{deviceId}/imageData")
  public void deviceImageDataCreate(@PathVariable long deviceId, @RequestHeader(name="X-ul-filename") String headerFilename, HttpServletRequest req, HttpServletResponse res) throws IOException {
    String filename = HttpUtils.base64Decode(headerFilename, null);
    serviceMgmt.deviceImageDataCreate(deviceId, filename, req.getInputStream());
  }
  
  @GetMapping("/app")
  public List<App> appsGet(@RequestParam(name="platform", required=false) Platform filterPlatform) {
    return serviceMgmt.appsGet(filterPlatform);
  } 

  @PostMapping("/app")
  public App appCreate(@RequestBody AppCreate body) {
    return serviceMgmt.appCreate(body);
  }

  @GetMapping("/app/{appId}")
  public App appGet(@PathVariable long appId) {
    return serviceMgmt.appGet(appId);
  }
  
  @PostMapping("/app/{appId}")
  public App appUpdate(@PathVariable long appId, @RequestBody AppUpdate body) {
    return serviceMgmt.appUpdate(appId, body);
  }  
  
  @PostMapping("/app/{appId}/version")
  public void appVersionCreate(@PathVariable long appId, @RequestHeader(name="X-ul-filename") String headerFilename, HttpServletRequest req, HttpServletResponse res) throws IOException {
    String filename = HttpUtils.base64Decode(headerFilename, null);
    serviceMgmt.versionCreate(appId, filename, req.getInputStream());
  }
  
  @GetMapping("/app/{appId}/version")
  public List<Version> appVersionsGet(@PathVariable long appId) {
    return serviceMgmt.versionsGet(appId);
  }
  
}
