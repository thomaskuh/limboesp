package net.limbomedia.esp.api;

import java.io.Serializable;

public class Esp8266Request implements Serializable {

  private static final long serialVersionUID = 1L;

  private String source;

  private String macSta;
  private String macAp;

  private Long sizeChip;
  private Long sizeFree;
  private Long sizeSketch;

  private String hashSketch;

  private String sdk;
  private String mode;
  private String version;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

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

  public Long getSizeChip() {
    return sizeChip;
  }

  public void setSizeChip(Long sizeChip) {
    this.sizeChip = sizeChip;
  }

  public Long getSizeFree() {
    return sizeFree;
  }

  public void setSizeFree(Long sizeFree) {
    this.sizeFree = sizeFree;
  }

  public Long getSizeSketch() {
    return sizeSketch;
  }

  public void setSizeSketch(Long sizeSketch) {
    this.sizeSketch = sizeSketch;
  }

  public String getHashSketch() {
    return hashSketch;
  }

  public void setHashSketch(String hashSketch) {
    this.hashSketch = hashSketch;
  }

  public String getSdk() {
    return sdk;
  }

  public void setSdk(String sdk) {
    this.sdk = sdk;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "Esp8266Request [source=" + source + ", macSta=" + macSta + ", macAp=" + macAp + ", sizeChip=" + sizeChip
        + ", sizeFree=" + sizeFree + ", sizeSketch=" + sizeSketch + ", hashSketch=" + hashSketch + ", sdk=" + sdk
        + ", mode=" + mode + ", version=" + version + "]";
  }

}
