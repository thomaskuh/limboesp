package net.limbomedia.esp.x.update;

import net.limbomedia.esp.x.update.api.UpdateRequest;

public class Normalizer {

    public static void normalize(UpdateRequest item) {
        item.setUuid(normalizeNullBlankLower(item.getUuid()));
        item.setHash(normalizeNullBlankLower(item.getHash()));
        item.setSecret(normalizeNullBlank(item.getSecret()));
    }

    private static String normalizeNullBlank(String in) {
        return (in != null && in.isBlank()) ? null : in;
    }

    private static String normalizeNullBlankLower(String in) {
        return (in != null && in.isBlank()) ? null : in.toLowerCase();
    }
}
