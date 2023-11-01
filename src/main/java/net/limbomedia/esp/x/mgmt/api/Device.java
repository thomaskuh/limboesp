package net.limbomedia.esp.x.mgmt.api;

import java.io.Serializable;
import net.limbomedia.esp.x.common.api.DeviceState;
import net.limbomedia.esp.x.common.api.ImageData;
import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.common.api.Version;

public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private DeviceState state;
    private Platform platform;
    private String uuid;
    private String secret;
    private String name;
    private String source;
    private long tsCreate;
    private long tsCheck;
    private Long tsUpdate;

    private String hashApp;

    private String info;

    private App app;
    private Version versionCurrent;
    private Version versionLatest;

    private ImageData imageData;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Version getVersionCurrent() {
        return versionCurrent;
    }

    public void setVersionCurrent(Version versionCurrent) {
        this.versionCurrent = versionCurrent;
    }

    public Version getVersionLatest() {
        return versionLatest;
    }

    public void setVersionLatest(Version versionLatest) {
        this.versionLatest = versionLatest;
    }

    public String getHashApp() {
        return hashApp;
    }

    public void setHashApp(String hashApp) {
        this.hashApp = hashApp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "Device [id=" + id + ", state=" + state + ", platform=" + platform + ", uuid=" + uuid + ", secret="
                + secret + ", name=" + name + "]";
    }
}
