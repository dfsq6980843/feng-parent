package com.fengx.test;

import lombok.AllArgsConstructor;

/**
 * @author fengXiong
 * @date 2019/12/12 2:04 下午
 */
public class Join {

    @AllArgsConstructor
    static class Domino implements Runnable {

        private Thread previous;

        @Override
        public void run() {
            try {
                previous.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        //TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate. end!");
    }
}
