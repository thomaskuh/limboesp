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
@Order(1)
public class ProtocolHandlerArduinoEsp32 implements ProtocolHandler {

	@Value("${proxyheader}")
	private String proxyHeader;

	@Autowired
	private ServiceUpdate serviceUpdate;

	public boolean canHandle(Platform platform, HttpServletRequest req) {
		String header = req.getHeader("x-esp32-mode");
		return Platform.ESP32 == platform && header != null && !header.isEmpty();
	}

	@Override
	public void handleDevice(Platform platform, HttpServletRequest req, HttpServletResponse res) {
		UpdateRequest x = new UpdateRequest();
		x.setPlatform(platform);
		x.setSource(HttpUtils.remoteAdr(req, proxyHeader));
		x.setUuid(req.getHeader("x-esp32-sta-mac"));
		x.setSecret(req.getHeader("x-esp32-version"));
		x.setHash(req.getHeader("x-esp32-sketch-sha256"));
		x.setWhat("spiffs".equals(req.getHeader("x-esp32-mode")) ? What.DATA : What.APP);
		x.setInfo("SizeFlash: " + req.getHeader("x-esp32-chip-size") + ", SizeFree: "
				+ req.getHeader("x-esp32-free-space"));

		serviceUpdate.updateDevice(x,
				new ResponderArduinoEsp32(res, HttpMethod.resolve(req.getMethod()) == HttpMethod.HEAD ? false : true));

	}

	@Override
	public void handleApp(String appKey, Platform platform, HttpServletRequest req, HttpServletResponse res) {
		UpdateRequest x = new UpdateRequest();
		x.setPlatform(platform);
		x.setSource(HttpUtils.remoteAdr(req, proxyHeader));
		x.setUuid(req.getHeader("x-esp32-sta-mac"));
		x.setSecret(req.getHeader("x-esp32-version"));
		x.setHash(req.getHeader("x-esp32-sketch-sha256"));
		x.setWhat("spiffs".equals(req.getHeader("x-esp32-mode")) ? What.DATA : What.APP);
		x.setInfo("SizeFlash: " + req.getHeader("x-esp32-chip-size") + ", SizeFree: "
				+ req.getHeader("x-esp32-free-space"));

		serviceUpdate.updateApp(appKey, x,
				new ResponderArduinoEsp32(res, HttpMethod.resolve(req.getMethod()) == HttpMethod.HEAD ? false : true));
	}

	// Explaination:
	// "x-esp32-sta-mac" -> MAC in STA mode like "A0:20:A6:0A:31:B7"(uppercase)
	// (used as UUID)
	// "x-esp32-version" -> Only free configurable field in Arduino API (used as
	// secret)
	// "x-esp32-sketch-sha256" -> Current firmware SHA256 like
	// "F4E0AD03553E81EF1BEDE71D30C26893DEF065AC3E768DBAFB5577D05B9C717E"(uppercase)
	// "x-esp32-mode" -> Update "sketch" or "spiffs" (converted to What)
	// "x-esp32-chip-size" -> Chip flash size
	// "x-esp32-free-space" -> Free size on next OTA partition, so firmware size
	// should be less.

	// Not caring about:
	// "user-agent" -> Static "ESP32-http-Update"
	// "x-esp32-ap-mac" -> MAC for AP mode like "A2:20:A6:0A:31:B7"
	// "x-esp32-sdk-version" -> SDK version like "v3.2-18-g977854975"
	// "x-esp32-sketch-md5" -> Current firmware MD5 like
	// "4f1192660805a0a4c207635ef2c479bc"(lowercase)
	// "x-esp32-sketch-size" -> Current firmware size.

}
