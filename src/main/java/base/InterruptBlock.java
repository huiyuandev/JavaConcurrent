package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 打断阻塞线程
 * 阻塞线程：调用了sleep、wait、join的线程，打断后会清空打断标记(置为false)
 */
@Slf4j(topic = "c.InterruptBlock")
public class InterruptBlock {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记：{}", t1.isInterrupted());
    }

}
