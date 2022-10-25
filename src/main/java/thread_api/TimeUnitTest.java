package thread_api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 建议用 TimeUnit 的 sleep 代替 Thread 的 sleep 来获得更好的可读性
 */
@Slf4j(topic = "c.TimeUnitTest")
public class TimeUnitTest {

    public static void main(String[] args) throws InterruptedException {
        log.debug("enter");
        // 可读性比Thread.sleep好
        TimeUnit.SECONDS.sleep(1); // 等价于Thread.sleep(1000);
        log.debug("end");
    }

}
