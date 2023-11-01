package net.limbomedia.esp.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "imagedata", uniqueConstraints = @UniqueConstraint(columnNames = {"fk_device_id", "nr"}))
public class ImageDataEntity extends ImageBinaryEntity {

    @OneToOne
    @JoinColumn(name = "fk_device_id", nullable = false, foreignKey = @ForeignKey(name = "fk__imagedata__device"))
    private DeviceEntity device;

    /**
     * Timestamp when image has been fetched by device (and hopefully flashed). 0
     * means not fetched yet, everything else will lead to "no update for device".
     */
    @Column(nullable = false)
    private long tsFetch;

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public long getTsFetch() {
        return tsFetch;
    }

    public void setTsFetch(long tsFetch) {
        this.tsFetch = tsFetch;
    }

    @Override
    public String toString() {
        return "ImageData [name=" + name + ", ts=" + ts + ", tsFetch=" + tsFetch + ", binId=" + binId + ", binSize="
                + binSize + ", binHash=" + binHash + "]";
    }
}
