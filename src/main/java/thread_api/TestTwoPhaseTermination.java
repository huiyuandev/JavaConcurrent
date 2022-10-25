package thread_api;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestTwoPhaseTermination")
public class TestTwoPhaseTermination {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3500);
        tpt.stop();
    }

}

/**
 * 两阶段终止
 */
@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread thread;

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                // 获取当前线程打断标记
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    // 正常运行打断，不会清除打断标记
                    log.debug("料理后事");
                    break;
                }

                try {
                    // 打断阻塞线程，清除打断标记
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    current.interrupt();
                }

            }
        }, "t");

        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

}
