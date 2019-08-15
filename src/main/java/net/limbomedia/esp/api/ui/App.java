package net.limbomedia.esp.api.ui;

import java.io.Serializable;

import net.limbomedia.esp.api.Platform;

public class App implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private long id;
  private Platform platform;
  private String name;
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public Platform getPlatform() {
    return platform;
  }
  public void setPlatform(Platform platform) {
    this.platform = platform;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
}
