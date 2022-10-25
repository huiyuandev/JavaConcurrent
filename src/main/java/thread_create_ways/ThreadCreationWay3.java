package thread_create_ways;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程创建方式2:使用FutureTask结合Thread
 * FutureTask 能够接收 Callable 类型的参数，用来处理有返回结果的情况
 */
@Slf4j(topic = "c.ThreadCreationWay3")
public class ThreadCreationWay3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                log.debug("running");
//                Thread.sleep(2000);
//                return 100;
//            }
//        });

        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running");
            Thread.sleep(2000);
            return 100;
        });

        Thread t1 = new Thread(task, "t1");
        t1.start();

        // 阻塞式等待返回结果
        log.debug("{}", task.get());
    }

}
