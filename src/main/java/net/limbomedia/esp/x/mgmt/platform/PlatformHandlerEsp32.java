package net.limbomedia.esp.x.mgmt.platform;

import com.google.common.hash.Funnels;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import net.limbomedia.esp.x.common.api.Platform;
import org.apache.commons.io.input.BoundedInputStream;
import org.springframework.stereotype.Service;

@Service
public class PlatformHandlerEsp32 implements PlatformHandler {

    private HashFunction hfSha256 = Hashing.sha256();

    public Platform getPlatform() {
        return Platform.ESP32;
    }

    /**
     * Default ESP32 bin images consist of: APP_IMAGE + SHA256(APP_IMAGE). So we
     * create hash only for APP_IMAGE part. We could also just read last 32 bytes or
     * at best do both and compare for equality.
     */
    @Override
    public String hashApp(long size, InputStream is) throws IOException {
        long sizeApp = size - 32;
        Hasher hasher = hfSha256.newHasher();
        InputStream isLimited = new BoundedInputStream(is, sizeApp);
        ByteStreams.copy(isLimited, Funnels.asOutputStream(hasher));
        return hasher.hash().toString();
    }

    /**
     * Data image is hashed the classic way with SHA256 over the whole binary.
     */
    @Override
    public String hashData(long size, InputStream is) throws IOException {
        Hasher hasher = hfSha256.newHasher();
        ByteStreams.copy(is, Funnels.asOutputStream(hasher));
        return hasher.hash().toString();
    }
}
