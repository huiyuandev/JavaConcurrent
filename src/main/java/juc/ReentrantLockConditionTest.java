package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Sleeper.sleep;

/**
 * ReentrantLock锁条件变量测试
 */
@Slf4j(topic = "c.ReentrantLockConditionTest")
public class ReentrantLockConditionTest {

    private static final ReentrantLock lock = new ReentrantLock();
    // 多个条件，各自唤醒各自的，不会出现虚假唤醒情况
    private static final Condition waitCigaretteSet = lock.newCondition(); // 等烟休息室
    private static final Condition waitTakeoutSet = lock.newCondition(); // 等外卖休息室
    static volatile boolean hasCigarette = false;
    static volatile boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("没有烟，没法干活");
                while (!hasCigarette) {
                    // 无烟则等待
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟了，可以去干活了");
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("没吃饭，没力气干活");
                while (!hasTakeout) {
                    // 无烟则等待
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("吃饱了，可以去干活了");
            } finally {
                lock.unlock();
            }
        }, "t2").start();

        sleep(1);

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("烟送到了");
                waitCigaretteSet.signal(); // 唤醒等烟线程
                hasCigarette = true;
            } finally {
                lock.unlock();
            }
        }, "t3").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("外卖送到了");
                waitTakeoutSet.signal();
                hasTakeout = true;
            } finally {
                lock.unlock();
            }
        }, "t4").start();

    }

}
