package demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    @Test
    public void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(
                () -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    Factorial.calculate(10);
                }
            );

        future.get();
        System.out.println("Thread: " + Thread.currentThread().getName());
    }

    @Test
    public void callBack() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            return Factorial.calculate(10);
        });

        future.thenApply(result -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            return result * 10;
        });

        System.out.println("result = " + future.get());
        System.out.println("Thread: " + Thread.currentThread().getName());
    }

    @Test
    public void callBack_aggregation() throws ExecutionException, InterruptedException {

    }
}

