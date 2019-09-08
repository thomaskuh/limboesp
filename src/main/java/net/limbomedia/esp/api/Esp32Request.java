package net.limbomedia.esp.api;

public class Esp32Request extends Esp8266Request {

  private static final long serialVersionUID = 1L;

  protected String hashSketchSha256;

  public String getHashSketchSha256() {
    return hashSketchSha256;
  }

  public void setHashSketchSha256(String hashSketchSha256) {
    this.hashSketchSha256 = hashSketchSha256;
  }

  @Override
  public String toString() {
    return "Esp32Request [source=" + source + ", userAgent=" + userAgent + ", macSta=" + macSta + ", macAp=" + macAp
        + ", sizeChip=" + sizeChip + ", sizeFree=" + sizeFree + ", sizeSketch=" + sizeSketch + ", hashSketchMd5="
        + hashSketchMd5 + ", hashSketchShd256=" + hashSketchSha256 + ", sdk=" + sdk + ", mode=" + mode + ", version="
        + version + "]";
  }

}
