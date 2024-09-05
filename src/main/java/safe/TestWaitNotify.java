package safe;

import lombok.extern.slf4j.Slf4j;

import static util.Sleeper.sleep;

/**
 * 等待唤醒
 * 1) sleep是Thread方法，而wait是Object的方法
 * 2) sleep不需要强制和 synchronized配合使用，但wait需要和synchronized一起用
 * 3) sleep在睡眠的同时，不会释放对象锁的，但wait在等待的时候会释放对象锁
 * 4) 它们状态都是TIMED_WAITING
 */
@Slf4j(topic = "c.TestWaitNotify")
public class TestWaitNotify {
    final static Object obj = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码....");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码....");
            }
        }, "t2").start();

        // 主线程两秒后执行
        sleep(2);
        log.debug("唤醒 obj 上其它线程");
        synchronized (obj) {
//            obj.notify(); // 唤醒obj上一个线程
            obj.notifyAll(); // 唤醒obj上所有等待线程
        }
    }
}
