package net.limbomedia.esp.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.limbomedia.esp.x.common.api.DeviceState;
import net.limbomedia.esp.x.common.api.Platform;

@Entity
@Table(name = "device")
public class DeviceEntity extends IdEntity {

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DeviceState state;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Platform platform;

	/**
	 * Global unique device identifier. Depending on the platform and whatever the
	 * device submits, this could be a MAC, UUID or something like this.
	 */
	@Column(nullable = false, unique = true)
	private String uuid;

	/**
	 * Optional secret. If not-null, requests must match to be permitted. Make sure
	 * it's not an empty string.
	 */
	@Column(nullable = true)
	private String secret;

	/**
	 * Optional human readable name for the device given by the user. Make sure it's
	 * not an empty string.
	 */
	@Column(nullable = true)
	private String name;

	/**
	 * Source of incoming device update requests. Usually device IP.
	 */
	@Column(nullable = false)
	private String source;

	/**
	 * Hash of current firmware. Algo depends on the platform and its capabilities
	 * most likely SHA-256 or MD5. Make sure it's lower cased and not an empty
	 * string. If defined it's matched against incoming requests to decide weather
	 * the device is on the latest version or if there's an update available. Null
	 * is ok too, but means that we cannot track current device version, thus always
	 * deliver the latest version and the device has to implement compare logic
	 * itself.
	 */
	@Column(nullable = true)
	private String hashFirmware;

	/**
	 * First appearance/creation of device.
	 */
	@Column(nullable = false)
	private long tsCreate;

	/**
	 * Lastest check.
	 */
	@Column(nullable = false)
	private long tsCheck;

	/**
	 * Lastest update delivery.
	 */
	@Column(nullable = true)
	private Long tsUpdate;

	/**
	 * Flexible field for additional information submitted by device and shown in
	 * admin gui. No technical parsing or processing. Make sure it's not an empty
	 * string.
	 * 
	 */
	@Column(nullable = true)
	private String info;

	/**
	 * Deliver latest version of this app to the device.
	 */
	@ManyToOne
	@JoinColumn(name = "fk_app_id", nullable = true, foreignKey = @ForeignKey(name = "fk__device__app"))
	private AppEntity app;

	/**
	 * Current version on device.
	 */
	@ManyToOne
	@JoinColumn(name = "fk_version_id", nullable = true, foreignKey = @ForeignKey(name = "fk__device__version"))
	private VersionEntity version;

	/**
	 * Data image for device.
	 */
	@OneToOne(mappedBy = "device")
	private ImageDataEntity imageData;

	public ImageDataEntity getImageData() {
		return imageData;
	}

	public void setImageData(ImageDataEntity imageData) {
		this.imageData = imageData;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getHashFirmware() {
		return hashFirmware;
	}

	public void setHashFirmware(String hashFirmware) {
		this.hashFirmware = hashFirmware;
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

	public AppEntity getApp() {
		return app;
	}

	public void setApp(AppEntity app) {
		this.app = app;
	}

	public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
	}

	public DeviceState getState() {
		return state;
	}

	public void setState(DeviceState state) {
		this.state = state;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", uuid=" + uuid + ", secret=" + secret + ", platform="
				+ platform + ", state=" + state + ", source=" + source + "]";
	}

}
