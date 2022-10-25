package thread_api;

import lombok.extern.slf4j.Slf4j;

/**
 * 调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态（阻塞）
 */
@Slf4j(topic = "c.TheadSleep")
public class TheadSleep {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                // Thread.sleep在哪个线程调用，就让那个线程睡眠
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t");

        t.start();
        log.debug("t1 state:{}", t.getState());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state:{}", t.getState());

    }

}
