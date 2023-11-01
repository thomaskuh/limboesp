package net.limbomedia.esp.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import net.limbomedia.esp.x.common.api.Platform;

@Entity
@Table(name = "app")
public class AppEntity extends IdEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true, unique = true, name = "kee")
    private String key;

    @OneToMany(mappedBy = "app")
    private Set<VersionEntity> versions = new HashSet<>();

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

    public Set<VersionEntity> getVersions() {
        return versions;
    }

    void addVersion(VersionEntity version) {
        this.versions.add(version);
    }

    void removeVersion(VersionEntity version) {
        this.versions.remove(version);
    }

    public long nextVersion() {
        return versions.stream().mapToLong(VersionEntity::getNr).max().orElse(0) + 1;
    }

    public VersionEntity getVersionLatest() {
        return versions.stream()
                .max(Comparator.comparingLong(VersionEntity::getNr))
                .orElse(null);
    }

    public VersionEntity getVersionByHash(String hash) {
        return versions.stream()
                .filter(x -> x.getBinHash().equals(hash))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "App [id=" + id + ", platform=" + platform + ", name=" + name + ", key=" + key + "]";
    }
}
