package com.xuwakao.mixture.framework.multiTask;

import android.os.Handler;
import android.os.HandlerThread;

import com.xuwakao.mixture.framework.AppConfig;
import com.xuwakao.mixture.framework.utils.MLog;

import java.lang.ref.WeakReference;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xujiexing on 13-11-1.
 * <p/>
 * TaskWatchDog is used to check whether task would had been done in specific timeout.
 */
public class TaskWatchDog {
    private static final String TAG = "WatchDog";
    private static TaskWatchDog mDog;
    private HandlerThread mDogThread;
    private Handler mHandler;
    private static AtomicInteger counter = new AtomicInteger(0);

    public static TaskWatchDog shareInstance() {
        if (mDog == null) {
            mDog = new TaskWatchDog();
        }
        return mDog;
    }

    private TaskWatchDog() {
        if (mDogThread == null) {
            mDogThread = new HandlerThread("WatchDog Thread");
            mDogThread.start();
            mHandler = new Handler(mDogThread.getLooper());
        }
    }

    /**
     * Check if task would be done after timeout
     *
     * @param task
     */
    public void checkTimeOutTask(AbsAysncFutureTaskWrapper task) {
        DogRunnable runnable = new DogRunnable(task);
        mHandler.postDelayed(runnable, task.getParam().timeout * 1000);
    }

    /**
     * Shut down watch dog.
     */
    public void shutDown() {
        MLog.debug(TAG, "WatchDog is shutting down");
        mDog = null;
        if (mDogThread.isAlive())
            mDogThread.interrupt();
        mDogThread = null;
        if (AppConfig.getInstance().isDebuggable()) {
            counter.set(0);
        }
    }

    private class DogRunnable implements Runnable {
        private WeakReference<AbsAysncFutureTaskWrapper> mTask;

        public DogRunnable(AbsAysncFutureTaskWrapper task) {
            mTask = new WeakReference<AbsAysncFutureTaskWrapper>(task);
        }

        @Override
        public void run() {
            if (mTask != null) {
                AbsAysncFutureTaskWrapper task = mTask.get();
                if (task != null && task.getTask() != null) {
                    synchronized (task) {
                        AbsAsyncFutureTask actualTask = task.getTask();
                        if (actualTask != null && !actualTask.hasCompleted()) {
                            if (AppConfig.getInstance().isDebuggable()) {
                                MLog.debug(TAG, "AbsAsyncTask is canceling because timeout + " + counter.incrementAndGet());
                            }
                            actualTask.cancel(true, true);
                        }
                    }
                }
            }
        }
    }
}
