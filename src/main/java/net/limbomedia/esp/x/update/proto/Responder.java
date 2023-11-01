package net.limbomedia.esp.x.update.proto;

import java.io.InputStream;
import net.limbomedia.esp.x.common.api.ImageData;
import net.limbomedia.esp.x.common.api.Version;
import org.kuhlins.lib.binstore.IoFunction;

public interface Responder {

    /**
     * Called when device is not configured for updates or already on latest
     * version.
     */
    void onNoUpdate();

    /**
     * Called when there's an app update available. Must return a consumer handling
     * the binary data stream or null if no data is requested.
     *
     * @param size Binary size.
     * @return
     */
    IoFunction<InputStream, Void> onData(Version version);

    /**
     * Called when there's an data update available. Must return a consumer handling
     * the binary data stream or null if no data is requested.
     *
     * @param size Binary size.
     * @return
     */
    IoFunction<InputStream, Void> onData(ImageData imageData);
}
