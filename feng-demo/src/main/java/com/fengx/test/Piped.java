package com.fengx.test;

import lombok.AllArgsConstructor;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author fengXiong
 * @date 2019/12/12 1:43 下午
 */
public class Piped {

    @AllArgsConstructor
    static class Print implements Runnable {

        private PipedReader in;

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        pipedWriter.connect(pipedReader);
        Thread printThread = new Thread(new Print(pipedReader), "PrintThread");
        printThread.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1) {
                pipedWriter.write(receive);
            }
        } finally {
            pipedWriter.close();
        }
    }
}
