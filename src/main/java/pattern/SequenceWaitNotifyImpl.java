package pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 两个线程t1、t2，要求执行顺序必须先2后1
 * wait&notify实现
 */
@Slf4j(topic = "c.SequenceWaitNotifyImpl")
public class SequenceWaitNotifyImpl {

    static final Object lock = new Object();
    // 标识t2是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
