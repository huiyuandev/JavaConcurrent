package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 非守护线程只要没有结束，JVM进程就不会结束
 * 在没有其他非守护线程运行的情况下，不管守护线程代码有没有运行完，JVM都会强制结束守护线程
 */
@Slf4j(topic = "c.DaemonThread")
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("结束");
        }, "t1");
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(1000);
        log.debug("结束");
    }
}
