package com.mapofzones.endpointchecker.common.threads;

public interface IThreadStarter {

    void startThreads(Runnable function);
    void waitMainThread();
    boolean isDone();

}
