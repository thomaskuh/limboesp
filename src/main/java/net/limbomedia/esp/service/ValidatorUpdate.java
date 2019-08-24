package net.limbomedia.esp.service;

import org.kuhlins.webkit.ex.ValidationException;
import org.kuhlins.webkit.ex.model.ErrorDetail;

import net.limbomedia.esp.api.Errors;
import net.limbomedia.esp.api.Esp8266Request;

public class ValidatorUpdate {
  
  public static void validate(Esp8266Request item) {
    ValidationException ve = new ValidationException();

    if(item.getMacSta() == null || item.getMacSta().isEmpty()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-sta-mac").withParam("value", item.getMacSta()).withParam("allowed", "MAC address"));
    }

    if(item.getMacAp() == null || item.getMacAp().isEmpty()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-ap-mac").withParam("value", item.getMacAp()).withParam("allowed", "MAC address"));
    }
    
    if(item.getSdk() == null || item.getSdk().isEmpty()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-sdk-version").withParam("value", item.getSdk()).withParam("allowed", "Not-null-empty string"));
    }
    

    if(item.getMode() == null || !("sketch".equals(item.getMode()) || "spiffs".equals(item.getMode()))) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-mode").withParam("value", item.getMode()).withParam("allowed", "sketch or spiffs"));
    }
    
    if(item.getHashSketch() == null || item.getHashSketch().isEmpty()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-sketch-md5").withParam("value", item.getHashSketch()).withParam("allowed", "Not-null-empty string"));
    }
    
    if(item.getSizeChip() == null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-chip-size").withParam("value", "" + item.getSizeChip()).withParam("allowed", "Number"));
    }
    if(item.getSizeSketch() == null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-sketch-size").withParam("value", "" + item.getSizeSketch()).withParam("allowed", "Number"));
    }
    if(item.getSizeFree() == null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "x-esp8266-free-size").withParam("value", "" + item.getSizeFree()).withParam("allowed", "Number"));
    }
    
    ve.throwOnDetails();
  }
  
}
