package juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子(字段)更新器
 * 利用字段更新器，可以针对对象的某个域（Field）进行原子操作，只能配合有volatile修饰的字段使用，否则会出现异常
 */
@Slf4j(topic = "c.AtomicReferenceFieldUpdaterDemo")
public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater
                .newUpdater(Student.class, String.class, "name");

        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
    }
}

class Student {

    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

}