package base;

import lombok.extern.slf4j.Slf4j;

import static util.Sleeper.sleep;

/**
 * 线程join方法：等待线程运行结束
 * 哪个线程调用join，就等待这个线程运行结束
 */
@Slf4j(topic = "c.ThreadJoin")
public class ThreadJoin {

    static int r = 0;

    static int r1 = 0;

    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
        test3();
    }

    /**
     * 等待单个线程运行结束
     */
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        }, "t1");

        t1.start();
        t1.join(); // 等待t1线程运行结束
        log.debug("r: {}", r);
        log.debug("结束");
    }

    /**
     * 等待多个线程运行结束
     */
    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(2);
            r2 = 20;
        });
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join begin");
        t2.join();
        log.debug("t2 join end");
        t1.join();
        log.debug("t1 join end");
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    /**
     * 有时限等待
     */
    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(2);
            r1 = 10;
        });

        long start = System.currentTimeMillis();
        t1.start();

        // 线程执行结束会导致 join 结束
        log.debug("join begin");
        // 有时限等待，1.5秒后结束等待（不管拿没拿到结果）
//        t1.join(1500);
        // 如果线程2秒后结束，join也会跟着结束，不会等够3秒在结束
        t1.join(3000);
        long end = System.currentTimeMillis();
        log.debug("r1: {} cost: {}", r1, end - start);
    }

}
