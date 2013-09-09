package com.xuwakao.mixture.framework.httpmodule;

import com.xuwakao.mixture.framework.utils.MLog;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by xujiexing on 13-9-3.
 */
public abstract class HttpAbstractTask<Params extends Comparable,Result> extends FutureTask<Result> implements Comparable<HttpAbstractTask<Params,Result>>{

    protected Params param;

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Callable}.
     *
     *
     * @param runnable the callable task
     * @throws NullPointerException if the callable is null
     */
    public HttpAbstractTask(Callable<Result> runnable, Params param) {
        super(runnable);
        this.param = param;
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
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbstractTask " + this + " has DONE with param = " + param +
                "in thread = " + Thread.currentThread() + " ###");
        super.done();
        try {
            successJob(this.get());
        } catch (ExecutionException e) {
            MLog.warn(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbstractTask has ExecutionException occured and the throwable message = " + e + " ###");
            this.exceptionalJob(e);
        }catch (CancellationException e){
            MLog.warn(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbstractTask has CancellationException occured and the throwable message = " + e + " ###");
            this.canceledJob();
        }
        /**
         * {@link java.util.concurrent.FutureTask#awaitDone} cause this exception,idealy it would not happen here,because work done
         */
        catch (InterruptedException e) {
            //TODO
        }
        finally {
            //TODO
        }
    }

    @Override
    public int compareTo(HttpAbstractTask<Params, Result> another) {
        return this.param.compareTo(another.param);
    }

    protected abstract void successJob(Result result);
    protected abstract void canceledJob();
    protected abstract void exceptionalJob(Exception e);

    public static abstract class HttpTaskRunnable<Params, Result> implements Callable<Result> {
        Params mParam;
    }
}
