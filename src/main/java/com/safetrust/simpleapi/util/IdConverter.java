package com.safetrust.simpleapi.util;

public class IdConverter {

    private IdConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static String fromId(long id) {
        return String.valueOf(id);
    }

    public static long toId(String maskedId) {
        return Long.parseLong(maskedId);
    }
}
