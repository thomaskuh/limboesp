package net.limbomedia.esp.x.update.proto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.limbomedia.esp.x.common.api.Platform;

public interface ProtocolHandler {

    boolean canHandle(Platform platform, HttpServletRequest req);

    void handleDevice(Platform platform, HttpServletRequest req, HttpServletResponse res);

    void handleApp(String appKey, Platform platform, HttpServletRequest req, HttpServletResponse res);
}
