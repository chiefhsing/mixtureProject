package com.xuwakao.mixture.framework.multiTask;

import android.os.Looper;

/**
 * Created by xujiexing on 13-9-23.
 */
public abstract class AbsBaseAsyncTask extends AbsAysncFutureTaskWrapper implements IAsyncTask {
    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    protected AbsBaseAsyncTask(TaskRequestParamBase param, Looper looper) {
        super(param, looper);
    }

    /**
     * Every task must be able to submit some computation to somewhere
     *
     * @return The caller itself
     */
    @Override
    public IAsyncTask submit() {
        TaskExecutor.getInstance().submit(this);
        return this;
    }

    @Override
    public void cancel(boolean mayInterruptIfRunning) {
        if (!this.getTask().isDone() && !this.getTask().isCancelled())
            this.getTask().cancel(mayInterruptIfRunning);
    }

    @Override
    protected boolean retryJob() {
        if (this.getParam().retryCount-- > 0) {
            TaskExecutor.getInstance().submit(this);
            return true;
        }
        return false;
    }
}
