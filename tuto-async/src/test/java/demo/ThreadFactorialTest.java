package demo;

import org.junit.jupiter.api.Test;

public class ThreadFactorialTest {
    private final Object lock = new Object();

    private Runnable createFactorialTask(int num, String threadName) {
        return () -> {
            long start = System.currentTimeMillis();
            long result = Factorial.calculate(num);
            long end = System.currentTimeMillis();

            synchronized (lock) {
                System.out.println(threadName + " [" + Thread.currentThread().threadId() + "] - Factorial of " + num + " is " + result);
                System.out.println(threadName + " execution time: " + (end - start) + " ms");
            }
        };
    }

    @Test
    public void testThreadFactorialThreadInheritance() {
        class MyThread extends Thread {
            private final int num;

            public MyThread(int num) {
                this.num = num;
            }

            @Override
            public void run() {
                System.out.println(Thread.currentThread().threadId() + ": Factorial of " + num + " is " + Factorial.calculate(num));
            }
        }

        Thread task = new MyThread(10);

        task.start();
    }

    @Test
    public void testThreadFactorialThreadRunnable() {
        int num = 10;
        Thread task = new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().threadId() + ": Factorial of " + num + " is " + Factorial.calculate(num));
            }
        });
        task.start();
    }

    @Test
    public void testThreadFactorialLambda() {
        int num = 10;
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().threadId() + ": Factorial of " + num + " is " + Factorial.calculate(num));
        });
        thread.start();
    }

    @Test
    public void testMultipleThread() {
        int num = 10;
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().threadId() + ": Factorial of " + num + " is " + Factorial.calculate(num));
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().threadId() + ": Factorial of " + num + " is " + Factorial.calculate(num));
        });

        thread1.start();
        thread2.start();
    }

    @Test
    public void testMultipleThreadAndMeasureTime() throws InterruptedException {
        int num = 10;

        Thread thread1 = new Thread(createFactorialTask(num, "Thread 1"));
        Thread thread2 = new Thread(createFactorialTask(num, "Thread 2"));

        long startTime = System.currentTimeMillis();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
}
