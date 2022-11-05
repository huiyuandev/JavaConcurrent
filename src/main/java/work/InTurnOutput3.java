package work;

import java.util.concurrent.locks.LockSupport;

/**
 * 交替输出
 * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。
 * 要求输出abcabcabcabcabc
 * park、unpark
 */
public class InTurnOutput3 {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) throws InterruptedException {
        ParkUnpark pu = new ParkUnpark(5);
        t1 = new Thread(() -> {
            pu.print("a", t2);
        });

        t2 = new Thread(() -> {
            pu.print("b", t3);
        });

        t3 = new Thread(() -> {
            pu.print("c", t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);

    }

}

class ParkUnpark {

    private int loopNumber; // 循环次数

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String content, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(content);
            LockSupport.unpark(next);
        }
    }

}