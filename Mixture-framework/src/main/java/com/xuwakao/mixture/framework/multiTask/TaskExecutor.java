package com.xuwakao.mixture.framework.multiTask;

import android.os.Handler;
import android.os.HandlerThread;

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
public class TaskExecutor extends AbsTaskExecutor {
    private static ThreadPoolExecutor mExecutor;
    private static BlockingQueue<Runnable> mQueue;

    private static TaskExecutor mSingletonInstance;

    private TaskExecutor() {
        super();
    }

    public static synchronized TaskExecutor getInstance() {
        if (mSingletonInstance == null) {
            mSingletonInstance = new TaskExecutor();
            doInit();
        }
        return mSingletonInstance;
    }

    private static void doInit() {
        TaskPriority.InvertedComparator comparator = new TaskPriority.InvertedComparator<TaskPriority>();
        mQueue = new PriorityBlockingQueue<Runnable>(ServiceConfig.EXECUTOR_QUEUE_INITIALIZED_CAPACITY, comparator);
        mExecutor = new ThreadPoolExecutor(ServiceConfig.EXECUTOR_CORE_POOL_MIN_SIZE,
                ServiceConfig.EXECUTOR_POOL_MAX_SIZE,
                ServiceConfig.EXCESS_THREAD_KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                mQueue,
                new TaskThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * Submit the task to Executor
     *
     * @param taskWrapper
     */
    @Override
    public synchronized void submit(AbsAysncFutureTaskWrapper taskWrapper) {
        mExecutor.execute(taskWrapper.getTask());
    }

    @Override
    public void shutDown() {
        mExecutor.shutdown();
        mSingletonInstance = null;
    }

    public static class TaskThreadFactory implements ThreadFactory {
        private AtomicInteger threadId = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "MultipleTask  # " + threadId.incrementAndGet() + " # Thread");
        }
    }
}
