package net.limbomedia.esp.api.update;

import java.io.Serializable;

public class UpdateRequestEsp8266 implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private String macSta;
  private String macAp;
  
  private long sizeChip;
  private long sizeFree;
  private long sizeSketch;
  
  private String hashSketch;

  public String getMacSta() {
    return macSta;
  }

  public void setMacSta(String macSta) {
    this.macSta = macSta;
  }

  public String getMacAp() {
    return macAp;
  }

  public void setMacAp(String macAp) {
    this.macAp = macAp;
  }

  public long getSizeChip() {
    return sizeChip;
  }

  public void setSizeChip(long sizeChip) {
    this.sizeChip = sizeChip;
  }

  public long getSizeFree() {
    return sizeFree;
  }

  public void setSizeFree(long sizeFree) {
    this.sizeFree = sizeFree;
  }

  public long getSizeSketch() {
    return sizeSketch;
  }

  public void setSizeSketch(long sizeSketch) {
    this.sizeSketch = sizeSketch;
  }

  public String getHashSketch() {
    return hashSketch;
  }

  public void setHashSketch(String hashSketch) {
    this.hashSketch = hashSketch;
  }

  @Override
  public String toString() {
    return "UpdateRequestEsp8266 [macSta=" + macSta + ", macAp=" + macAp + ", sizeChip=" + sizeChip + ", sizeFree="
        + sizeFree + ", sizeSketch=" + sizeSketch + ", hashSketch=" + hashSketch + "]";
  }
  
}
