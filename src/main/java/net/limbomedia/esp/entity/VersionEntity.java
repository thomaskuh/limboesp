package net.limbomedia.esp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "version", uniqueConstraints = @UniqueConstraint(columnNames = { "fk_app_id", "nr" }))
public class VersionEntity extends IdEntity {

  @ManyToOne
  @JoinColumn(name = "fk_app_id", nullable = false, foreignKey = @ForeignKey(name = "fk__version__app"))
  private AppEntity app;

  @Column(nullable = false)
  private long nr;

  /**
   * Optional human readable name. Must be at least an empty string instead of
   * null for easy handling.
   */
  @Column(nullable = false)
  private String name;

  @Column(name="binid", nullable = false)
  private String binId;
  
  @Column(name="binsize", nullable = false)
  private long binSize;
  
  @Column(name="binhash", nullable = false)
  private String binHash;
  
  public AppEntity getApp() {
    return app;
  };

  public void setApp(AppEntity app) {
    if (this.app != null) {
      this.app.removeVersion(this);
    }
    this.app = app;
    if (this.app != null) {
      this.app.addVersion(this);
    }
  }

  public long getNr() {
    return nr;
  }

  public void setNr(long nr) {
    this.nr = nr;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getBinId() {
    return binId;
  }
  
  public void setBinId(String binId) {
    this.binId = binId;
  }
  
  public long getBinSize() {
    return binSize;
  }
  
  public void setBinSize(long binSize) {
    this.binSize = binSize;
  }
  
  public String getBinHash() {
    return binHash;
  }
  
  public void setBinHash(String binHash) {
    this.binHash = binHash;
  }

}
