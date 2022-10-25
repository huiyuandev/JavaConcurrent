package thread_safe;

import lombok.extern.slf4j.Slf4j;

/**
 * 共享带来的线程安全问题
 */
@Slf4j(topic = "c.ThreadSafeProblem")
public class ThreadSafeProblem {

//    static int count = 0;
//    static Object lock = new Object();

    static Room room = new Room();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                /**
                 * 加锁，防止字节码指令交错执行
                 */
//                synchronized (lock) {
//                    count++;
//                }
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                /**
                 * 加锁，防止字节码指令交错执行
                 */
//                synchronized (lock) {
//                    count--;
//                }
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
//        log.debug("{}", count);
        log.debug("{}", room.getCount());

    }

}

// 面向对象改进
class Room {

    private int count = 0;

    // synchronized加在方法上，同步方法，锁是this对象
    public synchronized void increment() {
        count++;
        /**
         * synchronized加在代码块上，同步代码块
         */
//        synchronized (this) {
//            count++;
//        }
    }

    public synchronized void decrement() {
        count--;
//        synchronized (this) {
//            count--;
//        }
    }

    public synchronized int getCount() {
        return count;
//        synchronized (this) {
//            return count;
//        }
    }
}





