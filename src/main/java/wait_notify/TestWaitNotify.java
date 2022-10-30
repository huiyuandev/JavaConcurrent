package wait_notify;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.TestWaitNotify")
public class TestWaitNotify {

    final static Object obj = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (obj) {
                log.debug("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
//                    obj.wait(1000); // 有时限的等待
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
            obj.notify(); // 随机唤醒obj上等待的某一个线程
//            obj.notifyAll(); // 唤醒obj上所有等待线程
        }

    }

}
