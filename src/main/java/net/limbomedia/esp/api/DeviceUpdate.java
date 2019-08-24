package net.limbomedia.esp.api;

import java.io.Serializable;

public class DeviceUpdate implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;
  private DeviceState state;
  private Long appId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DeviceState getState() {
    return state;
  }

  public void setState(DeviceState state) {
    this.state = state;
  }

  public Long getAppId() {
    return appId;
  }

  public void setAppId(Long appId) {
    this.appId = appId;
  }

  @Override
  public String toString() {
    return "DeviceUpdate [name=" + name + ", state=" + state + ", appId=" + appId + "]";
  }

}
