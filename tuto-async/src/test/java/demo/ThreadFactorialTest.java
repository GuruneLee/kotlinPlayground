package demo;

import org.junit.jupiter.api.Test;

public class ThreadFactorialTest {
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
}
