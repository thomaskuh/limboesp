package net.limbomedia.esp.x.mgmt.api;

import java.io.Serializable;

public class AppUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String key;

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
		return "AppUpdate [name=" + name + ", key=" + key + "]";
	}

}
