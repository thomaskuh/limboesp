package net.limbomedia.esp.x.update.api;

public enum What {
	APP("app"), // APP
	DATA("data"); // DATA (Configuration / Spiffs / ...)

	private String value;

	private What(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	};

	public static What getByValue(String value) {
		for (What w : values()) {
			if (w.value.equalsIgnoreCase(value)) {
				return w;
			}
		}
		return APP;
	}
}
