package base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 睡眠方法更好实践，增强可读性
 */
@Slf4j(topic = "c.ThreadSleepBetter")
public class ThreadSleepBetter {

    public static void main(String[] args) throws InterruptedException {
        log.debug("enter...");
        TimeUnit.SECONDS.sleep(1);
        log.debug("end...");
    }

}
