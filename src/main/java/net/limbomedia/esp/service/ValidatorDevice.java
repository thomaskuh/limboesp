package net.limbomedia.esp.service;

import org.kuhlins.webkit.ex.ValidationException;
import org.kuhlins.webkit.ex.model.ErrorDetail;

import net.limbomedia.esp.api.DeviceState;
import net.limbomedia.esp.api.DeviceUpdate;
import net.limbomedia.esp.api.Errors;

public class ValidatorDevice {

  public static void validateUpdate(DeviceUpdate item) {
    ValidationException ve = new ValidationException();

    if (item.getName() == null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_DEVICE_NAME_INVALID.name()));
    }
    
    if(item.getState() == null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_DEVICE_STATE_INVALID.name()));
    }

    if(!DeviceState.APPROVED.equals(item.getState()) && item.getAppId() != null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_DEVICE_APP_UNAPPROVED.name()));
    }
    
    ve.throwOnDetails();
  }


}
