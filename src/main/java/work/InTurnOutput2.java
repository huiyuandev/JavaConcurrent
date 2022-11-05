package work;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替输出
 * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。
 * 要求输出abcabcabcabcabc
 * 可重入锁ReentrantLock await、signal
 */
public class InTurnOutput2 {

    public static void main(String[] args) throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();
        new Thread(() -> {
            awaitSignal.print("a", a, b);
        }).start();

        new Thread(() -> {
            awaitSignal.print("b", b, c);
        }).start();

        new Thread(() -> {
            awaitSignal.print("c", c, a);
        }).start();

        // 1秒后唤醒a
        Thread.sleep(1000);
        awaitSignal.lock();
        try {
            a.signal();
        } finally {
            awaitSignal.unlock();
        }

    }

}

class AwaitSignal extends ReentrantLock {

    private int loopNumber; // 循环次数

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    /**
     * 打印content
     *
     * @param content 需要打印的内容
     * @param current 当前休息室
     * @param next    下一个休息室
     */
    public void print(String content, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                System.out.print(content);
                next.signal(); // 唤醒下一个线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }

}