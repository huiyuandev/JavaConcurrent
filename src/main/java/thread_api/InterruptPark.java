package thread_api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 打断 park 线程，不会清空打断状态
 */
@Slf4j(topic = "c.InterruptPark")
public class InterruptPark {

    public static void main(String[] args) throws InterruptedException {
        testInterruptPark();
    }

    private static void testInterruptPark() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.debug("park");
            LockSupport.park();
            log.debug("unpark...");
//            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());

            // 此时如果需要线程暂停，需要清空打断状态
            log.debug("打断状态：{}", Thread.interrupted()); // 重置打断状态为false
            // 如果打断标记为true，则park失效
            LockSupport.park();
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            log.debug("unpark...");
        }, "t");
        t.start();

        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
    }

}
