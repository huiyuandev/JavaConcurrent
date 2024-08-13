package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程状态
 */
@Slf4j(topic = "c.ThreadState")
public class ThreadState {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
    }

}
