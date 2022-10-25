package thread_api;

import lombok.extern.slf4j.Slf4j;

/**
 * 打断正常运行的线程
 */
@Slf4j(topic = "c.ThreadInterruptNormal")
public class ThreadInterruptNormal {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                // 获取打断标记
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("被打断了，退出循环");
                    break;
                }
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt(); // 打断正常运行的线程，不会清空打断状态
        log.debug("打断标记：{}", t1.isInterrupted());
    }

}
