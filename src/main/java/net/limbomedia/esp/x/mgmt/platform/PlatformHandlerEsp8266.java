package net.limbomedia.esp.x.mgmt.platform;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.google.common.hash.Funnels;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;

import net.limbomedia.esp.x.common.api.Platform;

@Service
public class PlatformHandlerEsp8266 implements PlatformHandler {

	@SuppressWarnings("deprecation")
	private HashFunction hfMd5 = Hashing.md5();

	public Platform getPlatform() {
		return Platform.ESP8266;
	}

	@Override
	public String hashApp(long size, InputStream is) throws IOException {
		Hasher hasher = hfMd5.newHasher();
		ByteStreams.copy(is, Funnels.asOutputStream(hasher));
		return hasher.hash().toString();
	}

	@Override
	public String hashData(long size, InputStream is) throws IOException {
		Hasher hasher = hfMd5.newHasher();
		ByteStreams.copy(is, Funnels.asOutputStream(hasher));
		return hasher.hash().toString();
	}

}
