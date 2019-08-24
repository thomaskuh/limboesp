package net.limbomedia.esp.api;

import java.io.InputStream;

import org.kuhlins.binstore.IoFunction;

public interface Esp8266Response {

  /**
   * Called when device is not configured for updates or already on latest
   * version.
   */
  void onNoUpdate();

  /**
   * Called when there's an update available. Must return a consumer handling the
   * binary data stream.
   * 
   * @param size Binary size.
   * @return
   */
  IoFunction<InputStream, Void> onUpdate(Version version);

}
