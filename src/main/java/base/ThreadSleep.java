package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程睡眠后状态
 */
@Slf4j(topic = "c.ThreadSleep")
public class ThreadSleep {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        t1.start();
        log.debug("t1 state: {}", t1.getState());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.debug("t1 state: {}", t1.getState());

    }

}
