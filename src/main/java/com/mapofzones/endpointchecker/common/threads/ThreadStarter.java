package com.mapofzones.endpointchecker.common.threads;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class ThreadStarter implements IThreadStarter{

    private final ForkJoinPool pool;

    public ThreadStarter(Integer threadCount) {
        this.pool = new ForkJoinPool(threadCount);
    }

    @Override
    public void startThreads(Runnable function) {
        try {
            pool.submit(function).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("ThreadStarter: InterruptedException: " + e.getCause());
            e.printStackTrace();
        }
    }

}
