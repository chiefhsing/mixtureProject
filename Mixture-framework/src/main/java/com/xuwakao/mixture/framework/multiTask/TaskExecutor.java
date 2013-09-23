package com.xuwakao.mixture.framework.multiTask;

import com.xuwakao.mixture.framework.ServiceConfig;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xujiexing on 13-9-23.
 */
public class TaskExecutor {
    private static ThreadPoolExecutor executor;
    private static BlockingQueue<Runnable> queue;

    private static TaskExecutor worker;

    private TaskExecutor() {
        super();
    }

    public static synchronized TaskExecutor getInstance() {
        if (worker == null) {
            worker = new TaskExecutor();
            doInit();
        }
        return worker;
    }

    private static void doInit() {
        TaskPriority.InvertedComparator comparator = new TaskPriority.InvertedComparator<TaskPriority>();
        queue = new PriorityBlockingQueue<Runnable>(ServiceConfig.EXECUTOR_QUEUE_INITIALIZED_CAPACITY, comparator);
        executor = new ThreadPoolExecutor(ServiceConfig.EXECUTOR_CORE_POOL_MIN_SIZE,
                ServiceConfig.EXECUTOR_POOL_MAX_SIZE,
                ServiceConfig.EXCESS_THREAD_KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                queue,
                new TaskThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * Submit the task to Executor
     *
     * @param taskWrapper
     */
    public void submit(AbsAysncFutureTaskWrapper taskWrapper) {
        executor.execute(taskWrapper.getTask());
    }

    public static class TaskThreadFactory implements ThreadFactory {
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
            return new Thread(r, "MultipleTask  # " + threadId.incrementAndGet() + " # Thread");
        }
    }
}
