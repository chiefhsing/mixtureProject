package com.xuwakao.mixture.httpmodule;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by xujiexing on 13-9-3.
 */
public abstract class HttpAbstractTask<V> extends FutureTask<V>{
    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Callable}.
     *
     * @param runnable the callable task
     * @throws NullPointerException if the callable is null
     */
    public HttpAbstractTask(Callable<V> runnable) {
        super(runnable);
    }

    public static abstract class HttpTaskRunnable<Params, Result> implements Callable<Result> {
        Params mParams;
    }
}
