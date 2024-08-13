package base;

import lombok.extern.slf4j.Slf4j;

/**
 * 两阶段终止：在一个线程t1里“优雅”终止线程t2，“优雅”指给线程t2一个料理后事的机会
 */
@Slf4j(topic = "c.TwoPhaseTerminationTest")
public class TwoPhaseTerminationTest {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3500);
        tpt.stop();
    }

}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;

    // 启动监控
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000); // case1
                    log.debug("执行监控操作"); // case2
                } catch (InterruptedException e) {
                    // sleep会清空打断标记，因此需要再打断一次，将打断标记置为true
                    currentThread.interrupt();
                }
            }
        }, "monitor");

        monitor.start();
    }

    // 停止监控
    public void stop() {
        monitor.interrupt();
    }

}