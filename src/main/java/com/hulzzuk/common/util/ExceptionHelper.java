package com.hulzzuk.common.util;

public class ExceptionHelper {

    public static String getExceptionType(Throwable ex) {
        return ex != null ? ex.getClass().getName() : "Unknown";
    }

    public static String getExceptionMessage(Throwable ex) {
        return ex != null ? ex.getMessage() : "No message";
    }
}
