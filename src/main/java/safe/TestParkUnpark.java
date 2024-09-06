package safe;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static util.Sleeper.sleep;

/**
 * park&unpark
 * 与Object的wait&notify相比
 * 1.wait，notify和notifyAll必须配合Object Monitor一起使用，而park&unpark不必
 * 2.park&unpark是以线程为单位来【阻塞】和【唤醒】线程，而notify只能随机唤醒一个等待线程，
 * notifyAll是唤醒所有等待线程，就不那么【精确】
 * 3.park&unpark可以先unpark，而wait&notify不能先notify
 */
@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            sleep(2);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        sleep(1);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }

}
