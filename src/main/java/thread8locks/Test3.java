package thread8locks;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.Number3")
class Number3 {

    public synchronized void a() {
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

    public void c() {
        log.debug("3");
    }

}

@Slf4j(topic = "c.Test3")
public class Test3 {

    public static void main(String[] args) {
        Number3 n = new Number3();
        new Thread(() -> {
            log.debug("t1 begin");
            n.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 begin");
            n.b();
        }, "t2").start();

        new Thread(() -> {
            log.debug("t3 begin");
            n.c();
        }, "t3").start();
        /**
         * 3 1秒后 12
         * 32 1秒后 1
         * 23 1秒后 1
         */

    }

}
