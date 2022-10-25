package thread_create_ways;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程创建方式1:直接使用Thread
 */
@Slf4j(topic = "c.ThreadCreationWay1")
public class ThreadCreationWay1 {

    public static void main(String[] args) {
        // 创建线程对象
//        Thread t = new Thread(/* "t" 也可以在构造函数里指定线程名*/) {
//
//            @Override
//            public void run() {
//                // 要执行的任务
//                log.debug("running");
//            }
//
//        };

        // lambda
        Thread t = new Thread(() -> log.debug("running")); // 代码只有1行可省略大括号
        // 设置线程名
        t.setName("t");
        // 启动线程
        t.start();

        log.debug("running");
    }

}
