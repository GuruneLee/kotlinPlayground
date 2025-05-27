package demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

class CallableFutureTest {

    @Test
    void callable_void() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() {
                final String result = "Thread: " + Thread.currentThread().getName();
                System.out.println(result);
                return null;
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }


    @Test
    void callable_String() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return "Thread: " + Thread.currentThread().getName();
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }

    @Test
    void future() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Long> callable = new Callable<>() {

            @Override
            public Long call() throws InterruptedException {
                return Factorial.calculate(10);
            }
        };

        Future<Long> future = executorService.submit(callable);
        System.out.println(future.get());
        executorService.shutdown();
    }
}
