package net.limbomedia.esp.x.update;

import java.util.Optional;

import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.update.api.UpdateRequest;
import net.limbomedia.esp.x.update.proto.Responder;

public interface ServiceUpdate {

	Optional<Platform> getAppPlatform(String appKey);

	void updateDevice(UpdateRequest req, Responder responder);

	void updateApp(String appKey, UpdateRequest req, Responder responder);

}
