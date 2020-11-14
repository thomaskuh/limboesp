package net.limbomedia.esp.x.update.proto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuhlins.webkit.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.update.ServiceUpdate;
import net.limbomedia.esp.x.update.api.UpdateRequest;
import net.limbomedia.esp.x.update.api.What;

@Service
@Order(2)
public class ProtocolHandlerArduinoEsp8266 implements ProtocolHandler {

	@Value("${proxyheader}")
	private String proxyHeader;

	@Autowired
	private ServiceUpdate serviceUpdate;

	public boolean canHandle(Platform platform, HttpServletRequest req) {
		String header = req.getHeader("x-esp8266-mode");
		return Platform.ESP8266 == platform && header != null && !header.isEmpty();
	}

	@Override
	public void handleDevice(Platform platform, HttpServletRequest req, HttpServletResponse res) {
		UpdateRequest x = new UpdateRequest();
		x.setPlatform(platform);
		x.setSource(HttpUtils.remoteAdr(req, proxyHeader));
		x.setUuid(req.getHeader("x-esp8266-sta-mac"));
		x.setSecret(req.getHeader("x-esp8266-version"));
		x.setHash(req.getHeader("x-esp8266-sketch-md5"));
		x.setWhat("spiffs".equals(req.getHeader("x-esp8266-mode")) ? What.DATA : What.APP);
		x.setInfo("SizeFlash: " + req.getHeader("x-esp8266-chip-size") + ", SizeFree: "
				+ req.getHeader("x-esp8266-free-space"));

		serviceUpdate.updateDevice(x, new ResponderArduinoEsp8266(res,
				HttpMethod.resolve(req.getMethod()) == HttpMethod.HEAD ? false : true));
	}

	@Override
	public void handleApp(String appKey, Platform platform, HttpServletRequest req, HttpServletResponse res) {
		UpdateRequest x = new UpdateRequest();
		x.setPlatform(platform);
		x.setSource(HttpUtils.remoteAdr(req, proxyHeader));
		x.setUuid(req.getHeader("x-esp8266-sta-mac"));
		x.setSecret(req.getHeader("x-esp8266-version"));
		x.setHash(req.getHeader("x-esp8266-sketch-md5"));
		x.setWhat("spiffs".equals(req.getHeader("x-esp8266-mode")) ? What.DATA : What.APP);
		x.setInfo("SizeFlash: " + req.getHeader("x-esp8266-chip-size") + ", SizeFree: "
				+ req.getHeader("x-esp8266-free-space"));

		serviceUpdate.updateApp(appKey, x, new ResponderArduinoEsp8266(res,
				HttpMethod.resolve(req.getMethod()) == HttpMethod.HEAD ? false : true));
	}

	// Explaination:
	// "x-esp8266-sta-mac" -> MAC in STA mode like "A0:20:A6:0A:31:B7" (used as
	// UUID)
	// "x-esp8266-version" -> Only free configurable field in Arduino API (used as
	// secret)
	// "x-esp8266-sketch-md5" -> Current firmware SHA256.
	// "x-esp8266-mode" -> Update "sketch" or "spiffs" (converted to What)
	// "x-esp8266-chip-size" -> Chip flash size
	// "x-esp8266-free-space" -> Free size on next OTA partition, so firmware size
	// should be less.

	// Not caring about:
	// "user-agent" -> Static
	// "x-esp8266-ap-mac" -> MAC for AP mode like "A2:20:A6:0A:31:B7"
	// "x-esp8266-sdk-version" -> SDK version like "v3.2-18-g977854975"
}
