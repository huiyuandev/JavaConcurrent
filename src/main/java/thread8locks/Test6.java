package thread8locks;

import lombok.extern.slf4j.Slf4j;

import static utils.Sleeper.sleep;

@Slf4j(topic = "c.Number6")
class Number6 {

    // 静态锁住的是类对象（字节码对象）
    public static synchronized void a() {
        sleep(1);
        log.debug("1");
    }

    public static synchronized void b() {
        log.debug("2");
    }

}

@Slf4j(topic = "c.Test6")
public class Test6 {

    public static void main(String[] args) {
        Number6 n = new Number6();
        new Thread(() -> {
            log.debug("t1 begin");
            n.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 begin");
            n.b();
        }, "t2").start();

        /**
         * 2 1秒后1
         * 1秒后12
         */

    }

}
