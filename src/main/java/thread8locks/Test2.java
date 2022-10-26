package thread8locks;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.Number2")
class Number2 {

    public synchronized void a() {
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

}

@Slf4j(topic = "c.Test2")
public class Test2 {

    public static void main(String[] args) {
        Number2 n = new Number2();
        new Thread(() -> {
            log.debug("t1 begin");
            n.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 begin");
            n.b();
        }, "t2").start();
        /**
         * 1秒后12
         * 2 1秒后1
         */

    }

}
