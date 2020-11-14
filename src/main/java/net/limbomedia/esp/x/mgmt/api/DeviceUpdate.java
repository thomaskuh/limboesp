package net.limbomedia.esp.x.mgmt.api;

import java.io.Serializable;

import net.limbomedia.esp.x.common.api.DeviceState;

public class DeviceUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String secret;
	private DeviceState state;
	private Long appId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public DeviceState getState() {
		return state;
	}

	public void setState(DeviceState state) {
		this.state = state;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "DeviceUpdate [name=" + name + ", secret=" + secret + ", state=" + state + ", appId=" + appId + "]";
	}

}
