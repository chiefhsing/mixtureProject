package com.xuwakao.mixture.httpmodule;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by xujiexing on 13-9-3.
 */
public abstract class HttpAbstractTask<V> extends FutureTask<V>{
    private static final String TAG = "HttpAbstractTask";

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
        Log.i(TAG, "### HttpAbstractTask has DONE ###");
        super.done();
        try {
            success(this.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.w(TAG, "### HttpAbstractTask has InterruptedException occured and the throwable message = " + e.getMessage() + " ###");
            this.canceled();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.w(TAG, "### HttpAbstractTask has ExecutionException occured and the throwable message = " + e.getMessage() + " ###");
            this.exceptional(e);
        }finally {

        }
    }

    protected abstract void success(V result);
    protected abstract void canceled();
    protected abstract void exceptional(Exception e);

    public static abstract class HttpTaskRunnable<Params, Result> implements Callable<Result> {
        Params mParam;
    }
}
