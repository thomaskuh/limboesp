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
@Order(100)
public class ProtocolHandlerSlim implements ProtocolHandler {

	@Value("${proxyheader}")
	private String proxyHeader;

	@Autowired
	private ServiceUpdate serviceUpdate;

	public boolean canHandle(Platform platform, HttpServletRequest req) {
		return true;
	}

	@Override
	public void handleDevice(Platform platform, HttpServletRequest req, HttpServletResponse res) {
		UpdateRequest x = new UpdateRequest();
		x.setPlatform(platform);
		x.setSource(HttpUtils.remoteAdr(req, proxyHeader));
		x.setUuid(req.getHeader("x-uuid"));
		x.setSecret(req.getHeader("x-secret"));
		x.setHash(req.getHeader("x-hash"));
		x.setWhat(What.getByValue(req.getHeader("x-what")));
		x.setInfo(req.getHeader("x-info"));

		serviceUpdate.updateDevice(x,
				new ResponderSlim(res, HttpMethod.resolve(req.getMethod()) == HttpMethod.HEAD ? false : true));
	}

	@Override
	public void handleApp(String appKey, Platform platform, HttpServletRequest req, HttpServletResponse res) {
		UpdateRequest x = new UpdateRequest();
		x.setPlatform(platform);
		x.setSource(HttpUtils.remoteAdr(req, proxyHeader));
		x.setUuid(req.getHeader("x-uuid"));
		x.setSecret(req.getHeader("x-secret"));
		x.setHash(req.getHeader("x-hash"));
		x.setWhat(What.getByValue(req.getHeader("x-what")));
		x.setInfo(req.getHeader("x-info"));

		serviceUpdate.updateApp(appKey, x,
				new ResponderSlim(res, HttpMethod.resolve(req.getMethod()) == HttpMethod.HEAD ? false : true));
	}

}
