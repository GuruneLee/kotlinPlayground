package com.gurunelee;

import com.gurunelee.application.SpeedometerFactory;
import com.gurunelee.application.UrlSpeedometer;

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

        Long totalTime = urlSpeedometer.startAndMeasure(urls);
        urlSpeedometer.getResponseTimes().forEach((url, responseTime) ->
            System.out.println("URL: " + url + ", Response Time: " + responseTime + " ms")
        );
        System.out.println("Total Time: " + totalTime + " ms");
    }
}