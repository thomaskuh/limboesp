package net.limbomedia.esp.x.mgmt;

import net.limbomedia.esp.x.mgmt.api.AppUpdate;
import net.limbomedia.esp.x.mgmt.api.DeviceUpdate;

public class Normalizer {

	public static void normalize(AppUpdate item) {
		if (item.getKey() != null && item.getKey().isEmpty()) {
			item.setKey(null);
		}
	}

	public static void normalize(DeviceUpdate item) {
		item.setName(normalizeNullBlank(item.getName()));
		item.setSecret(normalizeNullBlank(item.getSecret()));
	}

	private static String normalizeNullBlank(String in) {
		return (in != null && in.isBlank()) ? null : in;
	}

}
