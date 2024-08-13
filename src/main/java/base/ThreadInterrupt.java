package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程打断
 * Thread.sleep():在哪个线程调用，就让那个线程睡眠
 */
@Slf4j(topic = "c.ThreadInterrupt")
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    throw new RuntimeException(e);
                }
            }
        };

        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
    }

}
