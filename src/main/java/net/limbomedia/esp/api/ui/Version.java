package net.limbomedia.esp.api.ui;

import java.io.Serializable;

public class Version implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private long nr;
  private String name;
  private String binId;
  private long binSize;
  private String binHash;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
