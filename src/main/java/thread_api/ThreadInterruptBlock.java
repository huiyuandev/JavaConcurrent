package thread_api;

import lombok.extern.slf4j.Slf4j;

/**
 * 打断阻塞
 * 其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException
 */
@Slf4j(topic = "c.ThreadInterrupt")
public class ThreadInterruptBlock {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            log.debug("enter sleep...");
            try {
                Thread.sleep(5000); // wait join
            } catch (InterruptedException e) {
                log.debug("wake up...");
                e.printStackTrace();
            }
        });
        t.setName("t");
        t.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t.interrupt(); // 打断sleep的线程，会清空打断状态
        log.debug("打断标记：{}", t.isInterrupted());
    }
}
