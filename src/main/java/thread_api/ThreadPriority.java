package thread_api;

/**
 * 线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它
 * 如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用
 * 线程优先级和yield（具体怎么调度取决于CPU）
 */
public class ThreadPriority {

    public static void main(String[] args) {
        Runnable r1 = () -> {
            int count = 0;
            for (; ; ) {
                System.out.println("----->1 " + count++);
            }
        };

        Runnable r2 = () -> {
            int count = 0;
            for (; ; ) {
//                Thread.yield();
                System.out.println("        ----->2 " + count++);
            }
        };

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();

    }

}
