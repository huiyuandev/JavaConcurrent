package thread_api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * join方法：等待线程运行结束
 */
@Slf4j(topic = "c.ThreadJoin")
public class ThreadJoin {

    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
//        testJoin();
//        testJoins();
        testWaitTimeJoin();
    }

    private static void testJoin() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        }, "t1");

        t1.start();
        t1.join();
        /**
         * 不使用join则主线程和t1线程交替执行，主线程的代码可能比t1线程
         * 先执行完成，导致拿不到r被修改后的值
         */
        log.debug("r = {}", r);
        log.debug("结束");
    }

    /**
     * 等待多个线程返回结果
     */
    private static void testJoins() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r2 = 20;
        }, "t2");

        t1.start();
        t2.start();

        long start = System.currentTimeMillis();
        log.debug("join begin");

        t2.join();
        log.debug("t2 join end");
        t1.join();
        log.debug("t1 join end");
        // 等价于下面代码
//        t1.join();
//        log.debug("t1 join end");
//        t2.join();
//        log.debug("t2 join end");

        long end = System.currentTimeMillis();
        log.debug("r1:{}, r2:{}, cost:{}", r1, r2, (end - start));
    }

    // 有时效的等待
    private static void testWaitTimeJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;

        }, "t1");

        long start = System.currentTimeMillis();
        t1.start();

        // 线程结束会导致join结束
        log.debug("join begin");
//        t1.join(1000); // 超过设定时间就不等了
        t1.join(3000); // 超过设定时间就不等了
        long end = System.currentTimeMillis();
        log.debug("r1:{}, cost:{}", r1, (end - start));
    }

}
