package safe;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程安全问题演示
 * 多个线程处理共享数据会产生线程安全问题
 */
@Slf4j(topic = "c.ThreadSafeProblem")
public class ThreadSafeProblem {

    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        log.debug("counter: {}", counter);

    }

}
