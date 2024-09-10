package safe;

import lombok.extern.slf4j.Slf4j;

/**
 * 两阶段终止volatile优化
 */
@Slf4j(topic = "c.TwoPhaseTerminationTest2")
public class TwoPhaseTerminationTest2 {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination2 tpt = new TwoPhaseTermination2();
        tpt.start();
        tpt.start();
        tpt.start();

        Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();
    }

}

/**
 * Balking（犹豫）模式用在一个线程发现另一个线程或本线程已经做了某一件相同的事，
 * 那么本线程就无需再做了，直接结束返回
 */
@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination2 {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false;
    // 判断是否执行过 start 方法
    private boolean starting = false;

    // 启动监控线程
    public void start() {
        synchronized (this) {
            if (starting) { // false
                return;
            }
            starting = true;
        }
        monitorThread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                // 是否被打断
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    // 停止监控线程
    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }

}
