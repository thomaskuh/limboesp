package net.limbomedia.esp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import net.limbomedia.esp.api.Platform;

@Entity
@Table(name = "device")
public class DeviceEntity extends IdEntity {

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Platform platform;

  /**
   * Global unique device identifier. Depending on the platform and whatever the
   * device submits, this could be a MAC, UUID or something like this.
   */
  @Column(nullable = false, unique = true)
  private String uuid;

  /**
   * Optional human readable name for the device given by the user. Must be at
   * least an empty string instead of null for easy handling.
   */
  @Column(nullable = false)
  private String name;

  /**
   * Hash of current firmware. Depending on the platform most likely something
   * like MD5 or SHA-X.
   */
  @Column(nullable = false)
  private String hashFirmware;
  
  @Column(nullable = false)
  private long tsCreate;
  
  @Column(nullable = false)
  private long tsCheck;
  
  @Column(nullable = true)
  private Long tsUpdate;

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
  
  public String getHashFirmware() {
    return hashFirmware;
  }
  
  public void setHashFirmware(String hashFirmware) {
    this.hashFirmware = hashFirmware;
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
