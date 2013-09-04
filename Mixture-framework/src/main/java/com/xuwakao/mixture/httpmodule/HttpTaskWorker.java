package com.xuwakao.mixture.httpmodule;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xujiexing on 13-9-4.
 */
public class HttpTaskWorker{
    private static ThreadPoolExecutor executor;
    private static LinkedBlockingQueue<Runnable> queue;

    private static HttpTaskWorker worker;

    private HttpTaskWorker() {
        super();
    }

    public static synchronized HttpTaskWorker getInstance(){
        if(worker == null){
            worker = new HttpTaskWorker();
            doInit();
        }
        return worker;
    }

    public void submit(HttpAbsTaskWrapper task){
        executor.execute(task.getTask());
    }

    private static void doInit() {
        queue = new LinkedBlockingQueue<Runnable>(10);
        executor = new ThreadPoolExecutor(HttpServiceConfig.EXECUTOR_CORE_POOL_MIN_SIZE,
                HttpServiceConfig.EXECUTOR_POOL_MAX_SIZE,
                HttpServiceConfig.EXCESS_THREAD_KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                queue,
                new HttpThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public static class HttpThreadFactory implements ThreadFactory{
        private AtomicInteger threadId = new AtomicInteger(0);

        /**
         * Constructs a new {@code Thread}.  Implementations may also initialize
         * priority, name, daemon status, {@code ThreadGroup}, etc.
         *
         * @param r a runnable to be executed by new thread instance
         * @return constructed thread, or {@code null} if the request to
         * create a thread is rejected
         */
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Http # " + threadId.incrementAndGet() + " # Thread");
        }
    }
}
