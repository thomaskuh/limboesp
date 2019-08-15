package net.limbomedia.esp.api.ui;

import java.io.Serializable;

import net.limbomedia.esp.api.Platform;

public class Device implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private Platform platform;
  private String uuid;
  private String name;
  private long tsCreate;
  private long tsCheck;
  private Long tsUpdate;

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

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getTsCreate() {
    return tsCreate;
  }

  public void setTsCreate(long tsCreate) {
    this.tsCreate = tsCreate;
  }

  public long getTsCheck() {
    return tsCheck;
  }

  public void setTsCheck(long tsCheck) {
    this.tsCheck = tsCheck;
  }

  public Long getTsUpdate() {
    return tsUpdate;
  }

  public void setTsUpdate(Long tsUpdate) {
    this.tsUpdate = tsUpdate;
  }

}
