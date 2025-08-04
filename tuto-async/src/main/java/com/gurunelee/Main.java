package com.gurunelee;

import com.gurunelee.application.SpeedometerFactory;
import com.gurunelee.application.UrlSpeedometer;
import com.gurunelee.application.speedometerimpl.SpeedResult;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Arrays.stream(args).filter((arg) -> arg.startsWith("-Dlevel="))
                .forEach((arg) -> {
                    String level = arg.substring("-Dlevel=".length());
                    System.setProperty("level", level);
                    System.out.println("Log level set to: " + level);
                });

        UrlSpeedometer urlSpeedometer = SpeedometerFactory.newInstance();
        String[] urls = {
                "https://www.google.com",
                "https://www.github.com",
                "https://www.stackoverflow.com"
        };

        urlSpeedometer.setUrls(urls);
        SpeedResult result = urlSpeedometer.getResponseTimesAsync();
        System.out.println("Total Time: " + result.measurementTime() + " ms");
    }
}