package work;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程执行顺序
 * 需求：有两个线程分别打印1和2，要求必须先打印2，后打印1
 * 等待唤醒 wait、notify
 */
@Slf4j(topic = "c.ThreadExecuteOrder")
public class ThreadExecuteOrder1 {

    static final Object lock = new Object();

    static boolean t2runned = false; // t2是否运行过

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();
            }
        }, "t2").start();

    }

}
