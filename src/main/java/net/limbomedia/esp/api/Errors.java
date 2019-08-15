package net.limbomedia.esp.api;

public enum Errors {

  VAL_APP_NAME_INVALID("Name must be specified."),
  VAL_APP_NAME_EXISTS("Name already exists."),
  VAL_APP_PLATFORM_INVALID("Platform must be specified."),
  
  VAL_VERSION_NAME_INVALID("Name/Filename must be specified."),
  VAL_VERSION_BIN_EMPTY("Binary not transfered or empty."),
  VAL_VERSION_BIN_DUPE("Same binary already bound to version {{no}} - {{name}}.");
  
  private Errors(String text) {}
  
}
