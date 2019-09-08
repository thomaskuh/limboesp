package net.limbomedia.esp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.limbomedia.esp.api.DeviceState;
import net.limbomedia.esp.api.Platform;

@Entity
@Table(name = "device")
public class DeviceEntity extends IdEntity {
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DeviceState state;
  
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
  
  @Column(nullable = false)
  private String source;
  
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
  
  @Column(nullable = true)
  private Long sizeChip;
  
  @Column(nullable = true)
  private Long sizeAppFree;
  
  @Column(nullable = true)
  private Long sizeAppUsed;
  
  @Column(nullable = true)
  private String info;
  
  /**
   * Deliver latest version of this app to the device.
   */
  @ManyToOne
  @JoinColumn(name = "fk_app_id", nullable = true, foreignKey = @ForeignKey(name = "fk__device__app"))
  private AppEntity app;

  /**
   * Current version on device.
   */
  @ManyToOne
  @JoinColumn(name = "fk_version_id", nullable = true, foreignKey = @ForeignKey(name = "fk__device__version"))
  private VersionEntity version;

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
  
  public String getSource() {
    return source;
  }
  
  public void setSource(String source) {
    this.source = source;
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
  
  public AppEntity getApp() {
    return app;
  }
  
  public void setApp(AppEntity app) {
    this.app = app;
  }
  
  public VersionEntity getVersion() {
    return version;
  }
  
  public void setVersion(VersionEntity version) {
    this.version = version;
  }

  public Long getSizeChip() {
    return sizeChip;
  }

  public void setSizeChip(Long sizeChip) {
    this.sizeChip = sizeChip;
  }

  public Long getSizeAppFree() {
    return sizeAppFree;
  }

  public void setSizeAppFree(Long sizeAppFree) {
    this.sizeAppFree = sizeAppFree;
  }

  public Long getSizeAppUsed() {
    return sizeAppUsed;
  }

  public void setSizeAppUsed(Long sizeAppUsed) {
    this.sizeAppUsed = sizeAppUsed;
  }
  
  public DeviceState getState() {
    return state;
  }
  
  public void setState(DeviceState state) {
    this.state = state;
  }
  
  public String getInfo() {
    return info;
  }
  
  public void setInfo(String info) {
    this.info = info;
  }

  public String toString() {
    return "Device [id=" + id + ", name=" + name + ", uuid=" + uuid + ", platform=" + platform + ", state=" + state + ", source=" + source + "]";
  }

}
