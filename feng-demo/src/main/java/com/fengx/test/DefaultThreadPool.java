package com.fengx.test;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fengXiong
 * @date 2019/12/12 5:40 下午
 */
@NoArgsConstructor
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORKER_NUMBERS = 10;

    private static final int DEFAULT_WORKER_NUMBERS = 5;

    private static final int MIN_WORKER_NUMBERS = 1;

    private final LinkedList<Job> jobs = Lists.newLinkedList();

    private final List<Worker> workers = Collections.synchronizedList(Lists.newArrayList());

    private int workerNum = DEFAULT_WORKER_NUMBERS;

    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : Math.max(num, MIN_WORKER_NUMBERS);
        initialWorkers(workerNum);
    }

    private void initialWorkers(int workerNum) {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet()).start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(Worker::shutdown);
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initialWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            int count = 0;
            while (count <= num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    class Worker implements Runnable {

        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (Exception e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
