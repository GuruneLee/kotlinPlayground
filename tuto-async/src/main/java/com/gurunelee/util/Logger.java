package com.gurunelee.util;

public class Logger {
    public static void threadLog(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] " + message);
    }
}
