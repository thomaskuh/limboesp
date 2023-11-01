package net.limbomedia.esp.x.mgmt.api;

public enum Errors {
    VAL_APP_NAME_INVALID("detail.val_app_name_invalid ", "Name must be specified."),
    VAL_APP_NAME_EXISTS("detail.val_app_name_exists", "Name already exists."),
    VAL_APP_KEY_INVALID("detail.val_app_key_invalid", "Key invalid. Only a-z,0-9,-,_ allowed."),
    VAL_APP_KEY_EXISTS("detail.val_app_key_exists", "Key already exists."),
    VAL_APP_PLATFORM_INVALID("detail.val_app_platform_invalid", "Platform must be specified."),

    VAL_VERSION_NAME_INVALID("detail.val_version_name_invalid", "Name/Filename must be specified."),
    VAL_VERSION_BIN_EMPTY("detail.val_version_bin_empty", "Binary not transfered or empty."),
    VAL_VERSION_BIN_DUPE("detail.val_version_bin_dupe", "Same binary already bound to version {{no}} - {{name}}."),

    VAL_DEVICE_NAME_INVALID("detail.val_device_name_invalid", "Name cannot be null."),
    VAL_DEVICE_STATE_INVALID("detail.val_device_state_invalid", "State cannot be null"),
    VAL_DEVICE_APP_UNAPPROVED("detail.val_device_app_unapproved", "App can only be set on approved devices.");

    private String key;
    private String text;

    private Errors(String key, String text) {
        this.key = key;
        this.text = text;
    }

    public String key() {
        return key;
    }

    public String text() {
        return text;
    }
}
