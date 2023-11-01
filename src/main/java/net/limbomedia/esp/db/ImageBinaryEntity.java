package net.limbomedia.esp.db;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class ImageBinaryEntity extends IdEntity {

    /**
     * Timestamp of create/upload.
     */
    @Column(nullable = false)
    protected long ts;

    /**
     * Optional human readable name. Must be at least an empty string instead of
     * null for easy handling.
     */
    @Column(nullable = false)
    protected String name;

    /**
     * Incrementing version number.
     */
    @Column(nullable = false)
    protected long nr;

    @Column(name = "binid", nullable = false)
    protected String binId;

    @Column(name = "binsize", nullable = false)
    protected long binSize;

    @Column(name = "binhash", nullable = false)
    protected String binHash;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNr() {
        return nr;
    }

    public void setNr(long nr) {
        this.nr = nr;
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
