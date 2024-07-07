package create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 创建线程3种方式
 */
@Slf4j(topic = "c.ThreadCreate")
public class ThreadCreate {

    public static void main(String[] args) {

//        create1();

//        create2();

        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                log.debug("running");
                Thread.sleep(1000);
                return "hello";
            }

        });

        new Thread(futureTask, "t1").start();

        try {
            String result = futureTask.get();
            // 主线程获取t1线程的结果
            log.debug("结果是：{}", result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.debug("running......");

    }

    private static void create2() {
        Runnable runnable = () -> log.debug("running......");
        Thread t1 = new Thread(runnable, "t1");
        t1.start();
    }

    private static void create1() {
        Thread t = new Thread() {

            @Override
            public void run() {
                log.debug("running......");
            }

        };
        t.setName("t1");
        t.start();
    }

}
