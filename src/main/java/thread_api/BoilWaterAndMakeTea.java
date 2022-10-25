package thread_api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 烧水泡茶
 * 需求：
 * 想泡壶茶喝。当时的情况是：开水没有；水壶要洗，茶壶、茶杯要洗；火已生了，茶叶也有了。
 * 怎么办？
 * 解决方案：
 * 洗好水壶，灌上凉水，放在火上；在等待水开的时间里，洗茶壶、洗茶杯、拿茶叶；等水开
 * 了，泡茶喝。
 * 用两个线程（两个人协作）模拟烧水泡茶过程。
 */
@Slf4j(topic = "c.BoilWaterAndMakeTea")
public class BoilWaterAndMakeTea {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            sleep(1);
            log.debug("烧水");
            sleep(5);
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            sleep(1);
            log.debug("洗茶杯");
            sleep(1);
            log.debug("拿茶叶");
            sleep(1);
            try {
                t1.join();
                log.debug("泡茶");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();

    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
