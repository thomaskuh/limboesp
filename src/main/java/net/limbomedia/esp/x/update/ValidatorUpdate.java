package net.limbomedia.esp.x.update;

import org.kuhlins.webkit.ex.ValidationException;
import org.kuhlins.webkit.ex.model.ErrorDetail;

import net.limbomedia.esp.x.update.api.Errors;
import net.limbomedia.esp.x.update.api.UpdateRequest;
import net.limbomedia.esp.x.update.api.What;

public class ValidatorUpdate {

	public static void validateForDevice(UpdateRequest item) {
		ValidationException ve = new ValidationException();

		if (item.getSource() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "source")
					.withParam("value", item.getSource()).withParam("allowed", "Source of request (IP, ...)"));
		}
		if (item.getPlatform() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "platform")
					.withParam("value", item.getPlatform().name()).withParam("allowed", "ESP32, ESP8266, ..."));
		}
		if (item.getUuid() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "uuid")
					.withParam("value", item.getUuid()).withParam("allowed", "Unique device id (UUID, MAC, Whatever)"));
		}
		if (item.getWhat() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "what")
					.withParam("value", item.getWhat().getValue()).withParam("allowed", "app, data"));
		}

		ve.throwOnDetails();
	}

	public static void validateForApp(UpdateRequest item) {
		ValidationException ve = new ValidationException();

		if (item.getSource() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "source")
					.withParam("value", item.getSource()).withParam("allowed", "Source of request (IP, ...)"));
		}
		if (item.getPlatform() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "platform")
					.withParam("value", item.getPlatform().name()).withParam("allowed", "ESP32, ESP8266"));
		}
		if (item.getWhat() == What.DATA) {
			ve.withDetail(new ErrorDetail(Errors.VAL_UPDATE_FIELD_INVALID.name()).withParam("name", "what")
					.withParam("value", item.getWhat().getValue())
					.withParam("allowed", "app (data not supported yet for app updates)"));
		}

		ve.throwOnDetails();
	}

}
