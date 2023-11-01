package net.limbomedia.esp.x.update.api;

import java.io.Serializable;
import net.limbomedia.esp.x.common.api.Platform;

public class UpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    // Set by server
    protected Platform platform;
    protected String source;

    // Given by client (and maybe converted)
    protected String uuid;
    protected String secret;
    protected String hash;
    protected What what;
    protected String info;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public What getWhat() {
        return what;
    }

    public void setWhat(What what) {
        this.what = what;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UpdateRequest [platform=" + platform + ", source=" + source + ", uuid=" + uuid + ", secret=" + secret
                + ", hash=" + hash + ", what=" + what + ", info=" + info + "]";
    }
}
