package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Sleeper.sleep;

/**
 * ReentrantLock锁超时测试
 */
@Slf4j(topic = "c.ReentrantLockTimeoutTest")
public class ReentrantLockTimeoutTest {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获取锁");
            try {
                // tryLock()不带参数，获取不到锁，立刻返回
                // 尝试获取锁，等待2秒后仍没有获取到锁则获取失败，直接返回
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("获取不到锁，返回");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁，返回");
                return;
            }
            try {
                log.debug("获取到锁");
            } finally {
                lock.unlock();
            }

        }, "t1");

        lock.lock();
        log.debug("获取到锁");
        t1.start();

        // 主线程1秒后释放锁
        sleep(1);
        log.debug("释放锁");
        lock.unlock();

    }

}
