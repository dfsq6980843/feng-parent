package com.fengx.test;

/**
 * @author fengXiong
 * @date 2019/12/12 5:38 下午
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutdown();

    void addWorkers(int num);

    void removeWorker(int num);

    int getJobSize();
}
