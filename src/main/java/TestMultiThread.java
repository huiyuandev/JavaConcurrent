import lombok.extern.slf4j.Slf4j;

/**
 * 测试多个线程交替执行
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                log.debug("running");
            }
        }, "t2");
        t2.start();
    }

}
