package net.limbomedia.esp.x.update;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import net.limbomedia.esp.web.LogUtil;
import net.limbomedia.esp.x.common.api.Platform;
import net.limbomedia.esp.x.update.api.Errors;
import net.limbomedia.esp.x.update.proto.ProtocolHandler;
import org.kuhlins.lib.webkit.ex.NotFoundException;
import org.kuhlins.lib.webkit.ex.ValidationException;
import org.kuhlins.lib.webkit.ex.model.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class ControllerUpdate {

    @Value("${proxyheader}")
    private String proxyHeader;

    @Autowired
    private ServiceUpdate serviceUpdate;

    @Autowired
    private List<ProtocolHandler> protocolHandlers;

    @Deprecated
    @RequestMapping(
            path = "/esp32",
            method = {RequestMethod.HEAD, RequestMethod.GET})
    public void legacyEsp32(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LogUtil.log(req);
        ProtocolHandler ph = protocolHandlers.stream()
                .filter(x -> x.canHandle(Platform.ESP32, req))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
        ph.handleDevice(Platform.ESP32, req, res);
    }

    @Deprecated
    @RequestMapping(
            path = "/esp8266",
            method = {RequestMethod.HEAD, RequestMethod.GET})
    public void legacyEsp8266(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LogUtil.log(req);
        ProtocolHandler ph = protocolHandlers.stream()
                .filter(x -> x.canHandle(Platform.ESP8266, req))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
        ph.handleDevice(Platform.ESP8266, req, res);
    }

    @RequestMapping(
            path = "/dev/esp32",
            method = {RequestMethod.HEAD, RequestMethod.GET})
    public void esp32(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LogUtil.log(req);
        ProtocolHandler ph = protocolHandlers.stream()
                .filter(x -> x.canHandle(Platform.ESP32, req))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
        ph.handleDevice(Platform.ESP32, req, res);
    }

    @RequestMapping(
            path = "/dev/esp8266",
            method = {RequestMethod.HEAD, RequestMethod.GET})
    public void esp8266(HttpServletRequest req, HttpServletResponse res) throws IOException {
        LogUtil.log(req);
        ProtocolHandler ph = protocolHandlers.stream()
                .filter(x -> x.canHandle(Platform.ESP8266, req))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
        ph.handleDevice(Platform.ESP8266, req, res);
    }

    @RequestMapping(
            path = "/app/{appKey}",
            method = {RequestMethod.HEAD, RequestMethod.GET})
    public void app(@PathVariable("appKey") String appKey, HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        LogUtil.log(req);

        Platform platform = serviceUpdate.getAppPlatform(appKey).orElseThrow(() -> new NotFoundException());

        ProtocolHandler ph = protocolHandlers.stream()
                .filter(x -> x.canHandle(platform, req))
                .findFirst()
                .orElseThrow(() -> new ValidationException()
                        .withDetail(new ErrorDetail(Errors.VAL_UPDATE_PROTOCOL_INVALID.name())));
        ph.handleApp(appKey, platform, req, res);
    }
}
