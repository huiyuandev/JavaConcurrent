package thread8locks;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Number1")
class Number1 {

    public synchronized void a() {
        log.debug("1");
    }

    public synchronized void b() {
        log.debug("2");
    }

}

@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        Number1 n = new Number1();
        new Thread(() -> {
            log.debug("t1 begin");
            n.a();
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 begin");
            n.b();
        }, "t2").start();
        /**
         * 输出12或21，cpu先调度哪个那个就先执行
         * 12概率较大，t1线程被调度的概率较大
         */
    }

}
