package net.limbomedia.esp.x.mgmt;

import java.io.InputStream;
import java.util.List;

import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.common.api.Version;
import net.limbomedia.esp.x.mgmt.api.App;
import net.limbomedia.esp.x.mgmt.api.AppCreate;
import net.limbomedia.esp.x.mgmt.api.AppUpdate;
import net.limbomedia.esp.x.mgmt.api.Device;
import net.limbomedia.esp.x.mgmt.api.DeviceUpdate;

public interface ServiceMgmt {

	List<Device> devicesGet();

	Device deviceGet(long deviceId);

	Device deviceUpdate(long deviceId, DeviceUpdate body);

	void deviceDelete(long deviceId);

	void deviceImageDataCreate(long deviceId, String filename, InputStream in);

	List<App> appsGet(Platform filterPlatform);

	App appGet(long appId);

	App appCreate(AppCreate item);

	App appUpdate(long appId, AppUpdate body);

	void appDelete(long appId);

	List<Version> versionsGet(long appId);

	void versionCreate(long appId, String filename, InputStream in);

}
