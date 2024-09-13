package threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join 是 JDK 1.7 加入的新的线程池实现，它体现的是一种分治思想，适用于能够进行任务拆分的 cpu 密集型
 * 运算
 * 所谓的任务拆分，是将一个大任务拆分为算法上相同的小任务，直至不能拆分可以直接求解。跟递归相关的一些计
 * 算，如归并排序、斐波那契数列、都可以用分治思想进行求解
 * Fork/Join 在分治的基础上加入了多线程，可以把每个任务的分解和合并交给不同的线程来完成，进一步提升了运
 * 算效率
 * Fork/Join 默认会创建与 cpu 核心数大小相同的线程池
 * 提交给 Fork/Join 线程池的任务需要继承 RecursiveTask（有返回值）或 RecursiveAction（没有返回值）
 */
public class TestForkJoin {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
//        System.out.println(pool.invoke(new AddTask1(5)));
        System.out.println(pool.invoke(new AddTask3(1, 5)));
    }
}

@Slf4j(topic = "c.AddTask")
class AddTask1 extends RecursiveTask<Integer> {

    int n;

    public AddTask1(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            log.debug("join() {}", n);
            return n;
        }
        AddTask1 t1 = new AddTask1(n - 1);

        t1.fork();
        log.debug("fork() {} + {}", n, t1);
        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);
        return result;
    }
}

@Slf4j(topic = "c.AddTask")
class AddTask2 extends RecursiveTask<Integer> {

    int begin;
    int end;

    public AddTask2(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }
        int mid = (end + begin) / 2;

        AddTask2 t1 = new AddTask2(begin, mid - 1);
        t1.fork();
        AddTask2 t2 = new AddTask2(mid + 1, end);
        t2.fork();
        log.debug("fork() {} + {} + {} = ?", mid, t1, t2);

        int result = mid + t1.join() + t2.join();
        log.debug("join() {} + {} + {} = {}", mid, t1, t2, result);
        return result;
    }
}

@Slf4j(topic = "c.AddTask")
class AddTask3 extends RecursiveTask<Integer> {

    int begin;
    int end;

    public AddTask3(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }
        int mid = (end + begin) / 2;

        AddTask3 t1 = new AddTask3(begin, mid);
        t1.fork();
        AddTask3 t2 = new AddTask3(mid + 1, end);
        t2.fork();
        log.debug("fork() {} + {} = ?", t1, t2);

        int result = t1.join() + t2.join();
        log.debug("join() {} + {} = {}", t1, t2, result);
        return result;
    }
}
