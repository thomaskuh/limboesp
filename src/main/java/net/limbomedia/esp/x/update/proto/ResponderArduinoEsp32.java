package net.limbomedia.esp.x.update.proto;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.kuhlins.binstore.IoFunction;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import net.limbomedia.esp.x.common.api.ImageData;
import net.limbomedia.esp.x.common.api.Version;

public class ResponderArduinoEsp32 extends ResponderAbstract {

	public ResponderArduinoEsp32(HttpServletResponse res, boolean withData) {
		super(res, withData);
	}

	@Override
	public IoFunction<InputStream, Void> onData(Version version) {
		res.setStatus(HttpStatus.OK.value());
		if (withData) {
			res.setContentType("application/octet-stream");
			res.setContentLengthLong(version.getBinSize());
			return is -> {
				StreamUtils.copy(is, res.getOutputStream());
				return null;
			};
		} else {
			res.setContentLengthLong(0l);
			return null;
		}
	}

	@Override
	public IoFunction<InputStream, Void> onData(ImageData imageData) {
		res.setStatus(HttpStatus.OK.value());
		if (withData) {
			res.setContentType("application/octet-stream");
			res.setContentLengthLong(imageData.getBinSize());
			return is -> {
				StreamUtils.copy(is, res.getOutputStream());
				return null;
			};
		} else {
			res.setContentLengthLong(0l);
			return null;
		}
	}

}
