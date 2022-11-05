package work;

/**
 * 交替输出
 * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。
 * 要求输出abcabcabcabcabc
 * 等待唤醒 wait、notify
 */
public class InTurnOutput1 {

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1, 5);
        new Thread(() -> {
            waitNotify.print("a", 1, 2);
        }).start();

        new Thread(() -> {
            waitNotify.print("b", 2, 3);
        }).start();

        new Thread(() -> {
            waitNotify.print("c", 3, 1);
        }).start();
    }

}

/**
 * 输出内容  等待标记  下一个标记
 * a          1      2
 * b          2      3
 * c          3      1
 */
class WaitNotify {

    private int flag;
    private int loopNumber; // 循环次数

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String content, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                // 标记不是等待标记则等待
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(content);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

}