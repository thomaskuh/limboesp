package net.limbomedia.esp.x.mgmt.api;

import java.io.Serializable;
import net.limbomedia.esp.x.common.api.Platform;

public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private Platform platform;
    private String name;
    private String key;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "App [id=" + id + ", platform=" + platform + ", name=" + name + ", key=" + key + "]";
    }
}
