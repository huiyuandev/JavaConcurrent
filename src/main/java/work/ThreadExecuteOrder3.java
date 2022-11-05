package work;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程执行顺序
 * 需求：有两个线程分别打印1和2，要求必须先打印2，后打印1
 * LockSupport park、unpark
 */
@Slf4j(topic = "c.ThreadExecuteOrder")
public class ThreadExecuteOrder3 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t2").start();

    }

}
