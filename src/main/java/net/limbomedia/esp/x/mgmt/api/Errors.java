package net.limbomedia.esp.x.mgmt.api;

public enum Errors {

	VAL_APP_NAME_INVALID("Name must be specified."),
	VAL_APP_NAME_EXISTS("Name already exists."),
	VAL_APP_KEY_INVALID("Key invalid. Only a-z,0-9,-,_ allowed."),
	VAL_APP_KEY_EXISTS("Key already exists."),
	VAL_APP_PLATFORM_INVALID("Platform must be specified."),

	VAL_VERSION_NAME_INVALID("Name/Filename must be specified."),
	VAL_VERSION_BIN_EMPTY("Binary not transfered or empty."),
	VAL_VERSION_BIN_DUPE("Same binary already bound to version {{no}} - {{name}}."),

	VAL_DEVICE_NAME_INVALID("Name cannot be null."),
	VAL_DEVICE_STATE_INVALID("State cannot be null"),
	VAL_DEVICE_APP_UNAPPROVED("App can only be set on approved devices.");

	private Errors(String text) {
	}

}
