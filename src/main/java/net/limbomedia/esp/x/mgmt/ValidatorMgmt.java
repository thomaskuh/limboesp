package net.limbomedia.esp.x.mgmt;

import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Pattern;

import org.kuhlins.webkit.ex.ValidationException;
import org.kuhlins.webkit.ex.model.ErrorDetail;

import net.limbomedia.esp.db.AppEntity;
import net.limbomedia.esp.db.RepoApp;
import net.limbomedia.esp.db.VersionEntity;
import net.limbomedia.esp.x.common.api.DeviceState;
import net.limbomedia.esp.x.mgmt.api.AppCreate;
import net.limbomedia.esp.x.mgmt.api.AppUpdate;
import net.limbomedia.esp.x.mgmt.api.DeviceUpdate;
import net.limbomedia.esp.x.mgmt.api.Errors;

public class ValidatorMgmt {

	private static Pattern PATTERN_KEY = Pattern.compile("^[0-9a-z\\-\\_]+$");

	public static void validate(DeviceUpdate item) {
		ValidationException ve = new ValidationException();

		if (item.getState() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_DEVICE_STATE_INVALID.name()));
		}

		if (!DeviceState.APPROVED.equals(item.getState()) && item.getAppId() != null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_DEVICE_APP_UNAPPROVED.name()));
		}

		ve.throwOnDetails();
	}

	public static void validate(AppCreate item, RepoApp repoApp) {
		ValidationException ve = new ValidationException();

		if (item.getName() == null || item.getName().isEmpty()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_INVALID.name()));
		}

		if (item.getName() == null || item.getName().isEmpty()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_INVALID.name()));
		}

		if (item.getPlatform() == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_APP_PLATFORM_INVALID.name()));
		}

		ve.throwOnDetails();

		if (repoApp.findOneByName(item.getName()).isPresent()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_EXISTS.name()));
		}

		ve.throwOnDetails();
	}

	public static void validate(AppUpdate item, AppEntity existingApp, RepoApp repoApp) {
		ValidationException ve = new ValidationException();

		if (item.getName() == null || item.getName().isEmpty()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_INVALID.name()));
		}

		if (item.getKey() != null) {
			if (!PATTERN_KEY.matcher(item.getKey()).matches()) {
				ve.withDetail(new ErrorDetail(Errors.VAL_APP_KEY_INVALID.name()));
			}
		}

		ve.throwOnDetails();

		if (!existingApp.getName().equals(item.getName()) && repoApp.findOneByName(item.getName()).isPresent()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_EXISTS.name()));
		}

		if (item.getKey() != null) {
			if (!item.getKey().equals(existingApp.getKey()) && repoApp.findOneByKey(item.getKey()).isPresent()) {
				ve.withDetail(new ErrorDetail(Errors.VAL_APP_KEY_EXISTS.name()));
			}
		}

		ve.throwOnDetails();
	}

	public static void validateVersionCreate1(String filename, InputStream is) {
		ValidationException ve = new ValidationException();

		if (filename == null || filename.isEmpty()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_NAME_INVALID.name()));
		}

		if (is == null) {
			ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_EMPTY.name()));
		}

		ve.throwOnDetails();
	}

	public static void validateVersionCreate2(AppEntity app, long binSize, String binHash) {
		ValidationException ve = new ValidationException();

		if (binSize == 0) {
			ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_EMPTY.name()));
		}

		Optional<VersionEntity> oVersion = app.getVersions().stream().filter(v -> v.getBinHash().equals(binHash))
				.findAny();
		if (oVersion.isPresent()) {
			ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_DUPE.name())
					.withParam("no", "" + oVersion.get().getNr()).withParam("name", oVersion.get().getName()));
		}

		ve.throwOnDetails();
	}

	public static void validateDeviceImageData(long binSize, String binHash) {
		ValidationException ve = new ValidationException();

		if (binSize == 0) {
			ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_EMPTY.name()));
		}

		ve.throwOnDetails();
	}

}
