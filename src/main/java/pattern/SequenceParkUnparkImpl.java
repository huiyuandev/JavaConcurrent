package pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 两个线程t1、t2，要求执行顺序必须先2后1
 * park&unpark实现
 */
@Slf4j(topic = "c.SequenceParkUnparkImpl")
public class SequenceParkUnparkImpl {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");


        Thread t2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t2");

        t1.start();
        t2.start();

    }
}
