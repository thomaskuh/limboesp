package net.limbomedia.esp.api;

import java.io.Serializable;

public class Esp8266Request implements Serializable {

  private static final long serialVersionUID = 1L;

  protected String userAgent;
  protected String source;

  protected String macSta;
  protected String macAp;

  protected Long sizeChip;
  protected Long sizeFree;
  protected Long sizeSketch;

  protected String hashSketchMd5;

  protected String sdk;
  protected String mode;
  protected String version;

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

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

  public String getHashSketchMd5() {
    return hashSketchMd5;
  }

  public void setHashSketchMd5(String hashSketchMd5) {
    this.hashSketchMd5 = hashSketchMd5;
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
    return "Esp8266Request [source=" + source + ", userAgent=" + userAgent + ", macSta=" + macSta + ", macAp=" + macAp
        + ", sizeChip=" + sizeChip + ", sizeFree=" + sizeFree + ", sizeSketch=" + sizeSketch + ", hashSketchMd5="
        + hashSketchMd5 + ", sdk=" + sdk + ", mode=" + mode + ", version=" + version + "]";
  }

}
