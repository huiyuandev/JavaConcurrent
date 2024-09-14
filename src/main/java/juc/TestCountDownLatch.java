package juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static util.Sleeper.sleep;

/**
 * CountdownLatch
 * 用来进行线程同步协作，等待所有线程完成倒计时。
 * 其中构造参数用来初始化等待计数值，await()用来等待计数归零，countDown()用来让计数减1
 */
@Slf4j(topic = "c.TestCountDownLatch")
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        test3();
    }

    private static void test5() {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> {
            log.debug("begin...");
            sleep(1);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        });
        service.submit(() -> {
            log.debug("begin...");
            sleep(1.5);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        });
        service.submit(() -> {
            log.debug("begin...");
            sleep(2);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        });
        service.submit(() -> {
            try {
                log.debug("waiting...");
                latch.await();
                log.debug("wait end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void test4() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            log.debug("begin...");
            sleep(1);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            sleep(2);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            sleep(1.5);
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        }).start();

        log.debug("waiting...");
        latch.await();
        log.debug("wait end...");
    }

    private static void test3() throws InterruptedException, ExecutionException {
        RestTemplate restTemplate = new RestTemplate();
        log.debug("begin");

        /**
         *  case 1：主线程获取结果
         */
//        Map<String, Object> response = restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
//        log.debug("end order: {}", response);
//        Map<String, Object> response1 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
//        log.debug("end product: {}", response1);
//        Map<String, Object> response2 = restTemplate.getForObject("http://localhost:8080/product/{2}", Map.class, 2);
//        log.debug("end product: {}", response1);
//        Map<String, Object> response3 = restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
//        log.debug("end logistics: {}", response3);

        /**
         *  case 2：CountDownLatch获取
         */
//        ExecutorService service = Executors.newCachedThreadPool();
//        CountDownLatch latch = new CountDownLatch(4);
//        service.submit(() -> {
//            Map<String, Object> response = restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
//            log.debug("end order: {}", response);
//            latch.countDown();
//        });
//        service.submit(() -> {
//            Map<String, Object> response1 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
//            log.debug("end product: {}", response1);
//            latch.countDown();
//        });
//        service.submit(() -> {
//            Map<String, Object> response2 = restTemplate.getForObject("http://localhost:8080/product/{2}", Map.class, 2);
//            log.debug("end product: {}", response2);
//            latch.countDown();
//        });
//        service.submit(() -> {
//            Map<String, Object> response3 = restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
//            log.debug("end logistics: {}", response3);
//            latch.countDown();
//        });
//        latch.await();
//        log.debug("执行完毕");

        /**
         *  case 3：Future获取
         */
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Map<String, Object>> f1 = service.submit(() -> {
            Map<String, Object> response = restTemplate.getForObject("http://localhost:8080/order/{1}", Map.class, 1);
            return response;
        });
        Future<Map<String, Object>> f2 = service.submit(() -> {
            Map<String, Object> response1 = restTemplate.getForObject("http://localhost:8080/product/{1}", Map.class, 1);
            return response1;
        });
        Future<Map<String, Object>> f3 = service.submit(() -> {
            Map<String, Object> response1 = restTemplate.getForObject("http://localhost:8080/product/{2}", Map.class, 2);
            return response1;
        });
        Future<Map<String, Object>> f4 = service.submit(() -> {
            Map<String, Object> response3 = restTemplate.getForObject("http://localhost:8080/logistics/{1}", Map.class, 1);
            return response3;
        });

        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());
        log.debug("执行完毕");
        service.shutdown();
    }

    private static void test2() throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        ExecutorService service = Executors.newFixedThreadPool(10, (r) -> {
            return new Thread(r, "t" + num.getAndIncrement());
        });
        CountDownLatch latch = new CountDownLatch(10);
        String[] all = new String[10];
        Random r = new Random();
        for (int j = 0; j < 10; j++) {
            int x = j;
            service.submit(() -> {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                    }
                    all[x] = Thread.currentThread().getName() + "(" + (i + "%") + ")";
                    System.out.print("\r" + Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n游戏开始...");
        service.shutdown();
    }
}
