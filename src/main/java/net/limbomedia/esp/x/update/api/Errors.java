package net.limbomedia.esp.x.update.api;

public enum Errors {
    VAL_UPDATE_FIELD_INVALID("Invalid field: {{name}}. Value: {{value}}. Allowed: {{allowed}}."),
    VAL_UPDATE_PLATFORM_MISMATCH("Platform mismatch."),
    VAL_UPDATE_SECRET_MISMATCH("Secret mismatch."),
    VAL_UPDATE_PROTOCOL_INVALID("Invalid protocol. Missing headers?");

    private Errors(String text) {}
}
