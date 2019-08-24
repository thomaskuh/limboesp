package net.limbomedia.esp.web;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.kuhlins.binstore.IoFunction;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import net.limbomedia.esp.api.Esp8266Response;
import net.limbomedia.esp.api.Version;

public class Esp8266HttpResponse implements Esp8266Response {
  
  private HttpServletResponse res;
  
  public Esp8266HttpResponse(HttpServletResponse res) {
    this.res = res;
  }
  
  @Override
  public void onNoUpdate() {
    res.setStatus(HttpStatus.NOT_MODIFIED.value());
  }

  @Override
  public IoFunction<InputStream, Void> onUpdate(Version version) {
    res.setStatus(HttpStatus.OK.value());
    res.setContentLengthLong(version.getBinSize());
    res.setContentType("application/octet-stream");
    res.setHeader("x-MD5", version.getBinHash());

    return is -> {
      StreamUtils.copy(is, res.getOutputStream());
      return null;
    };
  }
  
}
