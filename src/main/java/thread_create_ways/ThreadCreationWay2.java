package thread_create_ways;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程创建方式2:使用Runnable结合Thread
 */
@Slf4j(topic = "c.ThreadCreationWay2")
public class ThreadCreationWay2 {

    public static void main(String[] args) {

//        Runnable r = new Runnable() {
//
//            @Override
//            public void run() {
//                // 要执行的任务
//                log.debug("running");
//            }
//
//        };

        // lambda
        Runnable r = () -> log.debug("running"); // 1行代码大括号可省略

//        Thread t = new Thread(r, "t"); // 构造函数也可指定线程名
        Thread t = new Thread(r);
        // 设置线程名
        t.setName("t");
        // 启动线程
        t.start();

        log.debug("running");
    }

}
