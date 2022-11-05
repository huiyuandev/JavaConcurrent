package work;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程执行顺序
 * 需求：有两个线程分别打印1和2，要求必须先打印2，后打印1
 * 可重入锁 await、signal
 */
@Slf4j(topic = "c.ThreadExecuteOrder")
public class ThreadExecuteOrder2 {

    static final ReentrantLock lock = new ReentrantLock();
    static final Condition condition = lock.newCondition();

    static boolean t2runned = false; // t2是否运行过

    public static void main(String[] args) {

        new Thread(() -> {
            lock.lock();
            try {
                while (!t2runned) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("2");
                t2runned = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }, "t2").start();

    }

}
