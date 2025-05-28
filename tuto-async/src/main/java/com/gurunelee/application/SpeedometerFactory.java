package com.gurunelee.application;

import com.gurunelee.application.speedometerimpl.ThreadSpeedometer;

public abstract class SpeedometerFactory {
    public static UrlSpeedometer newInstance() {
        String speedometerType = System.getProperty("level", "1");

        return switch (speedometerType) {
            case "1" -> new ThreadSpeedometer();
//            case "2" -> new speedometerimpl.CompletableFutureSpeedometer();
//            case "3" -> new speedometerimpl.ReactiveSpeedometer();
            default -> throw new IllegalArgumentException("Unknown speedometer type: " + speedometerType);
        };
    }
}
