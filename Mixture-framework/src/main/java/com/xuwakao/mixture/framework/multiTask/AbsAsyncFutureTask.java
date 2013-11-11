package com.xuwakao.mixture.framework.multiTask;

import com.xuwakao.mixture.framework.ServiceConfig;
import com.xuwakao.mixture.framework.utils.MLog;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by xujiexing on 13-9-23.
 * <p/>
 * Subclass of {@link java.util.concurrent.FutureTask}.
 * It implement Comparable so that different instance can compare with each other.
 * This make available to order the priority of task.
 */
public abstract class AbsAsyncFutureTask<Params extends Comparable, Result> extends FutureTask<Result> implements Comparable<AbsAsyncFutureTask<Params, Result>> {
    public static final int NEW = 0;
    public static final int EXECUTING = 1;
    public static final int SUCCESS = 2;
    public static final int EXCEPTIONAL = 3;
    public static final int CANCELLED = 4;
    private volatile int state;

    /**
     * Used to judge whether task should be retried when task is timeout.
     * When timeout occur, task would be canceled.This cancel execution is different from
     * the cacel execution manually.
     */
    private boolean mShouldTimeoutRetry = false;

    /**
     * Task param;
     */
    protected Params param;

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Callable}.
     *
     * @param runnable the callable task
     * @throws NullPointerException if the callable is null
     */
    public AbsAsyncFutureTask(Callable<Result> runnable, Params param) {
        super(runnable);
        this.param = param;
        state = NEW;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.cancel(mayInterruptIfRunning, false);
    }

    public boolean cancel(boolean mayInterruptIfRunning, boolean shouldTimeoutRetryRetry) {
        mShouldTimeoutRetry = shouldTimeoutRetryRetry;
        return super.cancel(mayInterruptIfRunning);
    }

    /**
     * Protected method invoked when this task transitions to state
     * {@code isDone} (whether normally or via cancellation). The
     * default implementation does nothing.  Subclasses may override
     * this method to invoke completion callbacks or perform
     * bookkeeping. Note that you can query status inside the
     * implementation of this method to determine whether this task
     * has been cancelled.
     */
    @Override
    protected void done() {
        try {
            successJob(get());
            state = SUCCESS;
            MLog.debug(ServiceConfig.MULTIPLE_TASK_TAG, "### AbsAsyncTask has Successfully ###,task = " + this);
        } catch (ExecutionException e) {
            state = EXCEPTIONAL;
            MLog.warn(ServiceConfig.MULTIPLE_TASK_TAG, "### AbsAsyncTask has ExecutionException occured and the throwable message = " + e + " ###,task = " + this);
            exceptionalJob(e);
        } catch (CancellationException e) {
            state = CANCELLED;
            MLog.warn(ServiceConfig.MULTIPLE_TASK_TAG, "### AbsAsyncTask has CancellationException occured and the throwable message = " + e + " ###,task = " + this);
            canceledJob();
        }
        /**
         * {@link java.util.concurrent.FutureTask#awaitDone} cause this exception,idealy it would not happen here,because work doneExecution
         */ catch (InterruptedException e) {
            state = EXCEPTIONAL;
            MLog.warn(ServiceConfig.MULTIPLE_TASK_TAG, "### AbsAsyncTask has InterruptedException occured and the throwable message = " + e + " ###,task = " + this);
            //TODO
        } finally {
            //TODO
        }
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean hasCompleted() {
        return this.state >= SUCCESS;
    }

    public boolean isShouldTimeoutRetry() {
        return mShouldTimeoutRetry;
    }

    public void setShouldTimeoutRetry(boolean mShouldRetry) {
        this.mShouldTimeoutRetry = mShouldRetry;
    }

    /**
     * A task compares to another depend on its param.
     *
     * @param another another Task to compare
     * @return
     */
    @Override
    public int compareTo(AbsAsyncFutureTask<Params, Result> another) {
        return this.param.compareTo(another.param);
    }

    protected abstract void successJob(Result result);

    protected abstract void canceledJob();

    protected abstract void exceptionalJob(Exception e);

    public static abstract class AbsTaskRunnable<Params, Result> implements Callable<Result> {
        protected WeakReference<AbsAsyncFutureTask> mTask;
        Params mParam;

        public void setTask(AbsAsyncFutureTask mTask) {
            this.mTask = new WeakReference<AbsAsyncFutureTask>(mTask);
        }

        public AbsAsyncFutureTask getTask() {
            return this.mTask.get();
        }
    }
}
