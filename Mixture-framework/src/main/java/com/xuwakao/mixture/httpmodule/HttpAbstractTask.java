package com.xuwakao.mixture.httpmodule;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by xujiexing on 13-9-3.
 */
public abstract class HttpAbstractTask<V> extends FutureTask<V>{

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Callable}.
     *
     *
     * @param runnable the callable task
     * @throws NullPointerException if the callable is null
     */
    public HttpAbstractTask(Callable<V> runnable) {
        super(runnable);
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
        Log.i(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbstractTask has DONE ###");
        super.done();
        try {
            successJob(this.get());
        } catch (ExecutionException e) {
            Log.w(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbstractTask has ExecutionException occured and the throwable message = " + e + " ###");
            this.exceptionalJob(e);
        }catch (CancellationException e){
            Log.w(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbstractTask has CancellationException occured and the throwable message = " + e + " ###");
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

    protected abstract void successJob(V result);
    protected abstract void canceledJob();
    protected abstract void exceptionalJob(Exception e);

    public static abstract class HttpTaskRunnable<Params, Result> implements Callable<Result> {
        Params mParam;
    }
}
