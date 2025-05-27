package demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

/**
 * Executor 는 오직 실행에 대한 책임만을 갖는다
 */
public class ExecutorTest {
    @Test
    void runExecutor() {
        class RunExecutor implements Executor {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        }

        new RunExecutor().execute(() -> Factorial.calculate(10));
    }

    @Test
    void startExecutor() {
        class StartExecutor implements Executor {

            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        }

        new StartExecutor().execute(() -> Factorial.calculate(10));
    }
}
