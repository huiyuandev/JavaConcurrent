package thread_api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 */
@Slf4j(topic = "c.DaemonThread")
public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("结束");
        }, "t");
        // 设置该线程为守护线程
        t.setDaemon(true);
        t.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("主线程运行结束");
    }

}
