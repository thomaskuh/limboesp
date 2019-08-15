package net.limbomedia.esp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.limbomedia.esp.api.Platform;

@Entity
@Table(name = "app")
public class AppEntity extends IdEntity {

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Platform platform;
  
  @Column(nullable = false, unique = true)
  private String name;
  
  @OneToMany(mappedBy="app")
  private Set<VersionEntity> versions = new HashSet<>();
  
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
  
  public Set<VersionEntity> getVersions() {
    return versions;
  }
  
  void addVersion(VersionEntity version) {
    this.versions.add(version);
  }
  
  void removeVersion(VersionEntity version) {
    this.versions.remove(version);
  }
  
  public long nextVersion() {
    return versions.stream().mapToLong(VersionEntity::getNr).max().orElse(0) + 1;
  }

}
