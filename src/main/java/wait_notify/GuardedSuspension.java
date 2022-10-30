package wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 同步模式之保护性暂停
 * 用于一个线程等待另一个线程的结果
 */
@Slf4j(topic = "c.GuardedSuspension")
public class GuardedSuspension {

    public static void main(String[] args) {
        GuardObject guardObject = new GuardObject();
        new Thread(() -> {
            log.debug("等待结果");
            List<String> responses = (List<String>) guardObject.get();
            log.debug("结果大小：{}", responses.size());
        }, "t1").start();

        new Thread(() -> {
            log.debug("执行下载");
            try {
                List<String> download = Downloader.download();
                guardObject.complete(download);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();

    }

}

class GuardObject {

    private final Object lock = new Object();

    private Object response;

    public Object get() {
        synchronized (lock) { // 获取锁
            // 条件不满足则等待
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (lock) {
            this.response = response;
            lock.notifyAll(); // 条件满足，唤醒等待的线程
        }
    }

}