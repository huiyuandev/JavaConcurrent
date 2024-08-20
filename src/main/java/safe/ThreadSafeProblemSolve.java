package safe;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程安全问题解决方案1：加锁synchronized
 */
@Slf4j(topic = "c.ThreadSafeProblemSolve")
public class ThreadSafeProblemSolve {

    static int counter = 0;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("counter: {}", counter);

    }

}
