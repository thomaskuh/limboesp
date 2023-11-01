package net.limbomedia.esp.db;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "version", uniqueConstraints = @UniqueConstraint(columnNames = {"fk_app_id", "nr"}))
public class VersionEntity extends ImageBinaryEntity {

    @ManyToOne
    @JoinColumn(name = "fk_app_id", nullable = false, foreignKey = @ForeignKey(name = "fk__version__app"))
    private AppEntity app;

    public AppEntity getApp() {
        return app;
    }
    ;

    public void setApp(AppEntity app) {
        if (this.app != null) {
            this.app.removeVersion(this);
        }
        this.app = app;
        if (this.app != null) {
            this.app.addVersion(this);
        }
    }

    @Override
    public String toString() {
        return "Version [app=" + app + ", nr=" + nr + ", name=" + name + ", ts=" + ts + ", binId=" + binId
                + ", binSize=" + binSize + ", binHash=" + binHash + "]";
    }
}
