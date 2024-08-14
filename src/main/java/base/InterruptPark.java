package base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static util.Sleeper.sleep;

/**
 * 打断park线程
 */
@Slf4j(topic = "c.InterruptPark")
public class InterruptPark {

    private static void test2() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();

        sleep(1);
        t1.interrupt();
    }

    private static void test1() {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            // isInterrupted()：获取打断标记，不会清除打断标记
//            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());

            // interrupted()：获取打断标记后会清除打断标记
            log.debug("打断状态：{}", Thread.interrupted());
            // 打断标记为true park失效；要使park在打断后再次调用能继续生效，可以调用Thread.interrupted()方法清除打断标记
            LockSupport.park();
            log.debug("unpark...");
        }, "t1");
        t1.start();

        sleep(1);
        t1.interrupt();

    }

    public static void main(String[] args) {
        test1();
    }

}
