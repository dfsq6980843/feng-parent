package com.fengx.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fengXiong
 * @date 2019/12/10 2:14 下午
 */
public class DeadLockDemo {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private int i = 0;

    public static void main(String[] args) {
        DeadLockDemo deadLockDemo = new DeadLockDemo();
        List<Thread> threads = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    deadLockDemo.count();
                    deadLockDemo.safeCount();
                }
            });
            threads.add(thread);
        }
        threads
                .forEach(Thread::start);

        threads
                .forEach(thread -> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(deadLockDemo.i);
        System.out.println(atomicInteger.get());
        System.out.println(System.currentTimeMillis() - start);

    }

    public void count() {
        i++;
    }

    public void safeCount() {
        atomicInteger.getAndIncrement();
    }
}
