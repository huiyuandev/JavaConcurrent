package safe;

import lombok.extern.slf4j.Slf4j;

import static util.Sleeper.sleep;

@Slf4j(topic = "c.Test8Locks")
public class Test8Locks {

    public static void main(String[] args) {
        test7();
    }

    private static void test1() {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin");
            // case1
//            n1.a1();
            // case2
            n1.a2();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }).start();
    }

    private static void test2() {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a2();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.c();
        }).start();
    }

    private static void test3() {
        Number n1 = new Number();
        Number n2 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a2();
        }).start();
        new Thread(() -> {
            log.debug("begin");
            n2.b();
        }).start();
    }

    private static void test4() {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a3();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.b();
        }).start();
    }

    private static void test5() {
        Number n1 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a3();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n1.b2();
        }).start();
    }

    private static void test6() {
        Number n1 = new Number();
        Number n2 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a3();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n2.b();
        }).start();
    }

    private static void test7() {
        Number n1 = new Number();
        Number n2 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a3();
        }).start();

        new Thread(() -> {
            log.debug("begin");
            n2.b2();
        }).start();
    }

}

@Slf4j(topic = "c.Number")
class Number {

    public synchronized void a1() {
        log.debug("1");
    }

    public synchronized void a2() {
        sleep(1);
        log.debug("1");
    }

    public static synchronized void a3() {
        sleep(1);
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }
    public static synchronized void b2() {
        log.debug("2");
    }

    public void c() {
        log.debug("3");
    }

}
