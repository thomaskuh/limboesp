package net.limbomedia.esp.x.mgmt.api;

import java.io.Serializable;
import net.limbomedia.esp.x.common.api.Platform;

public class AppCreate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Platform platform;
    private String name;

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

    @Override
    public String toString() {
        return "AppCreate [platform=" + platform + ", name=" + name + "]";
    }
}
