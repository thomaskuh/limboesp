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
import org.springframework.web.bind.annotation.RestController;

import net.limbomedia.esp.api.ui.App;
import net.limbomedia.esp.api.ui.AppCreate;
import net.limbomedia.esp.api.ui.Device;
import net.limbomedia.esp.api.ui.Version;
import net.limbomedia.esp.service.ServiceMgmt;

@RestController
@RequestMapping("/api")
public class ControllerUI {
   
  @Autowired
  private ServiceMgmt serviceMgmt;
  
  @GetMapping("/device")
  public List<Device> devicesGet() {
    return serviceMgmt.devicesGet();
  }
  
  @GetMapping("/app")
  public List<App> appsGet() {
    return serviceMgmt.appsGet();
  } 

  @PostMapping("/app")
  public App appCreate(@RequestBody AppCreate body) {
    return serviceMgmt.appCreate(body);
  }

  @GetMapping("/app/{appId}")
  public App appGet(@PathVariable long appId) {
    return serviceMgmt.appGet(appId);
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
