package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动线程
 */
@Slf4j(topic = "c.ThreadMethod")
public class ThreadStart {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };
        t1.start();

        log.debug("running...");

    }

}
