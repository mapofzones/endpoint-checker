package com.mapofzones.endpointchecker.common.threads;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class ThreadStarter implements IThreadStarter{

    private final Integer threadCount;
    private final ThreadFactory threadFactory;

    private final List<CompletableFuture<Void>> cfList = new ArrayList<>();
    private ExecutorService executorService;

    public ThreadStarter(Integer threadCount,
                         String threadNaming) {
        this.threadCount = threadCount;
        this.threadFactory = new ThreadFactoryBuilder().setNameFormat(threadNaming).build();

    }

    @Override
    public void startThreads(Runnable function) {
        if (isDone()) {
            this.executorService = Executors.newFixedThreadPool(threadCount, threadFactory);
            run(function);
            executorService.shutdown();
        }
    }

    @Override
    public void waitMainThread() {
        if (!cfList.isEmpty()) {
            for (CompletableFuture<Void> voidCompletableFuture : cfList) {
                voidCompletableFuture.join();
            }
        }
    }

    @Override
    public boolean isDone() {
        if (!cfList.isEmpty())
            return cfList.stream().allMatch(CompletableFuture::isDone);
        else return true;
    }

    private void run(Runnable function) {
        cfList.clear();
        for (int i = 0; i < threadCount; i++) {

            CompletableFuture<Void> cf = CompletableFuture
                    .runAsync(function, executorService);

            cfList.add(cf);
        }
    }
}
