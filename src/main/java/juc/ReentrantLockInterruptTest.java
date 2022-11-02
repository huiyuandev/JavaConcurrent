package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static utils.Sleeper.sleep;

/**
 * ReentrantLock锁可打断测试
 */
@Slf4j(topic = "c.ReentrantLockInterruptTest")
public class ReentrantLockInterruptTest {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                /**
                 * 如果没有竞争那么此方法就会获取lock对象锁
                 * 有竞争就进入阻塞队列，可以被其他线程用interrupt方法打断
                 */
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁，返回");
                return;
            }
            try {
                log.debug("获取到锁");
            } finally {
                lock.unlock();
            }

        }, "t1");

        lock.lock(); // lock()方法不可打断，获取不到锁则无限等待
        t1.start();

        // 睡眠1秒后打断t1
        sleep(1);
        log.debug("打断 t1");
        t1.interrupt();
    }

}
