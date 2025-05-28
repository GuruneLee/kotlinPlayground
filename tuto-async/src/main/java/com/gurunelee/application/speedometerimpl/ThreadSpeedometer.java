package com.gurunelee.application.speedometerimpl;

import com.gurunelee.application.UrlSpeedometer;
import com.gurunelee.networking.UrlCaller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public class ThreadSpeedometer implements UrlSpeedometer {
    private final UrlCaller urlCaller;

    private final ConcurrentMap<String, Long> responseTimes = new ConcurrentHashMap<>();

    public ThreadSpeedometer() {
        this.urlCaller = new UrlCaller();
    }

    @Override
    public Long startAndMeasure(String... urls) {
        List<Thread> tasks = Stream.of(urls)
                .map(this::callUrlAndSetResponseTime)
                .map(Thread::new)
                .toList();

        long startTime = System.currentTimeMillis();

        tasks.forEach(Thread::start);
        tasks.forEach(task -> {
            try {
                task.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        });

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @Override
    public Map<String, Long> getResponseTimes() {
        return responseTimes;
    }

    private Runnable callUrlAndSetResponseTime(String url) {
        return () -> {
            Long responseTime = urlCaller.callAndMeasure(url);
            responseTimes.put(url, responseTime);
        };
    }
}
