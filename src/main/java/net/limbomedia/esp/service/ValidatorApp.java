package net.limbomedia.esp.service;

import java.io.InputStream;
import java.util.Optional;

import org.kuhlins.webkit.ex.ValidationException;
import org.kuhlins.webkit.ex.model.ErrorDetail;

import net.limbomedia.esp.api.AppCreate;
import net.limbomedia.esp.api.AppUpdate;
import net.limbomedia.esp.api.Errors;
import net.limbomedia.esp.entity.AppEntity;
import net.limbomedia.esp.entity.VersionEntity;
import net.limbomedia.esp.repo.RepoApp;

public class ValidatorApp {

  public static void validateCreate(AppCreate item, RepoApp repoApp) {
    ValidationException ve = new ValidationException();

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
  
  public static void validateUpdate(AppEntity existingApp, AppUpdate item, RepoApp repoApp) {
    ValidationException ve = new ValidationException();

    if (item.getName() == null || item.getName().isEmpty()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_INVALID.name()));
    }
    
    ve.throwOnDetails();
    
    if (!existingApp.getName().equals(item.getName()) && repoApp.findOneByName(item.getName()).isPresent()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_APP_NAME_EXISTS.name()));
    }

    ve.throwOnDetails();
  }  
  
  public static void validateVersionCreate(String filename, InputStream is) {
    ValidationException ve = new ValidationException();

    if (filename == null || filename.isEmpty()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_NAME_INVALID.name()));
    }
    
    if(is == null) {
      ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_EMPTY.name()));
    }

    ve.throwOnDetails();
  }

  public static void validateVersionCreate2(AppEntity app, long binSize, String binHash) {
    ValidationException ve = new ValidationException();

    if(binSize == 0) {
      ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_EMPTY.name()));
    }
    
    Optional<VersionEntity> oVersion = app.getVersions().stream().filter(v -> v.getBinHash().equals(binHash)).findAny();
    if(oVersion.isPresent()) {
      ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_DUPE.name()).withParam("no", "" + oVersion.get().getNr()).withParam("name", oVersion.get().getName()));
    }

    ve.throwOnDetails();
  }

  public static void validateDeviceImageData(long binSize, String binHash) {
    ValidationException ve = new ValidationException();

    if(binSize == 0) {
      ve.withDetail(new ErrorDetail(Errors.VAL_VERSION_BIN_EMPTY.name()));
    }
	    
    ve.throwOnDetails();
  }  

}
