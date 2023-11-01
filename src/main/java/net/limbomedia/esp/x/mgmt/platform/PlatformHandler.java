package net.limbomedia.esp.x.mgmt.platform;

import java.io.IOException;
import java.io.InputStream;
import net.limbomedia.esp.x.common.api.Platform;

public interface PlatformHandler {
    Platform getPlatform();

    String hashApp(long size, InputStream is) throws IOException;

    String hashData(long size, InputStream is) throws IOException;
}
