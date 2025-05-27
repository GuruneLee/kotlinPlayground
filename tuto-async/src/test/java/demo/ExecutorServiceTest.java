package demo;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ExecutorService 는 작업 등록의 책임을 갖는다.
 * 또한, Executor 를 상속받아 실행의 책임도 갖는다.
 */
public class ExecutorServiceTest {
    @Test
    public void shutDown() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());
        executorService.execute(runnable);

        // shutdown 호출
        executorService.shutdown();

        // shutdown 호출 이후에는 새로운 작업들을 받을 수 없음, 에러 발생
        assertThrows(RejectedExecutionException.class, () -> executorService.execute(runnable));
    }

    @Test
        //  출처: https://mangkyu.tistory.com/259 [MangKyu's Diary:티스토리]
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            final String result = "Hello";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> mang = () -> {
            Thread.sleep(4000L);
            final String result = "Java";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> kyu = () -> {
            Thread.sleep(2000L);
            final String result = "kyu";
            System.out.println("result = " + result);
            return result;
        };

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, mang, kyu));
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }

        System.out.println("time = " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();
    }
}
