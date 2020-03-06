package com.fengx.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author fengXiong
 * @date 2019/12/17 5:51 下午
 */
public class TwinsLockTest {

    public static void main(String[] args) {
        TwinsLockTest twinsLockTest = new TwinsLockTest();
        twinsLockTest.test();
    }

    public void test() {
        final Lock lock = new TwinsLock();


        class Worker extends Thread {

            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
            System.out.println(Thread.currentThread().getName());
        }
    }
}
