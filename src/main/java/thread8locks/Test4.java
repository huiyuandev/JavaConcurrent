package thread8locks;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.Number4")
class Number4 {

    public synchronized void a() {
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

}

@Slf4j(topic = "c.Test4")
public class Test4 {

    public static void main(String[] args) {
        Number4 n1 = new Number4();
        Number4 n2 = new Number4();
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
