package net.limbomedia.esp.x.update.proto;

import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import net.limbomedia.esp.x.common.api.ImageData;
import net.limbomedia.esp.x.common.api.Version;
import org.kuhlins.lib.binstore.IoFunction;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

public class ResponderArduinoEsp8266 extends ResponderAbstract {

    public ResponderArduinoEsp8266(HttpServletResponse res, boolean withData) {
        super(res, withData);
    }

    @Override
    public IoFunction<InputStream, Void> onData(Version version) {
        res.setStatus(HttpStatus.OK.value());
        res.setHeader("x-MD5", version.getBinHash());
        if (withData) {
            res.setContentType("application/octet-stream");
            res.setContentLengthLong(version.getBinSize());
            return is -> {
                StreamUtils.copy(is, res.getOutputStream());
                return null;
            };
        } else {
            return null;
        }
    }

    @Override
    public IoFunction<InputStream, Void> onData(ImageData imageData) {
        res.setStatus(HttpStatus.OK.value());
        res.setHeader("x-MD5", imageData.getBinHash());
        if (withData) {
            res.setContentType("application/octet-stream");
            res.setContentLengthLong(imageData.getBinSize());
            return is -> {
                StreamUtils.copy(is, res.getOutputStream());
                return null;
            };
        } else {
            return null;
        }
    }
}
