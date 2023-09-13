package com.training.rledenev.bankapp.services.impl;

public final class ServiceUtils {
    private ServiceUtils() {
    }

    public static String getEnumName(String string) {
        return string.toUpperCase().replaceAll("\\s", "_");
    }
}
