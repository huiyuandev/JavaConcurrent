package thread8locks;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.Number7")
class Number7 {

    // 静态锁住的是类对象（字节码对象）
    public static synchronized void a() {
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

}

@Slf4j(topic = "c.Test7")
public class Test7 {

    public static void main(String[] args) {
        Number7 n1 = new Number7();
        Number7 n2 = new Number7();
        new Thread(() -> {
            log.debug("t1 begin");
            n1.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 begin");
            n2.b();
        }, "t2").start();

        /**
         * 2 1秒后1
         */

    }

}
