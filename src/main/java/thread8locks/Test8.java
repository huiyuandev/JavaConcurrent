package thread8locks;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.Number8")
class Number8 {

    // 静态锁住的是类对象（字节码对象）
    public static synchronized void a() {
        sleep(1);
        log.debug("1");
    }

    public static synchronized void b() {
        log.debug("2");
    }

}

@Slf4j(topic = "c.Test8")
public class Test8 {

    public static void main(String[] args) {
        Number8 n1 = new Number8();
        Number8 n2 = new Number8();
        new Thread(() -> {
            log.debug("t1 begin");
            n1.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 begin");
            n2.b();
        }, "t2").start();

        /**
         * 1秒后12
         * 2 1秒后1
         */

    }

}
