package com.xuwakao.mixture.framework.multiTask;

import android.os.Looper;

import com.xuwakao.mixture.framework.ServiceConfig;
import com.xuwakao.mixture.framework.utils.MLog;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

        if (param == null || looper == null)
            throw new NullPointerException();
    }

    /**
     * Every task must be able to submit some computation to somewhere
     *
     * @return The caller itself
     */
    @Override
    public IAsyncTask submit() {
//        MLog.verbose(ServiceConfig.MULTIPLE_TASK_TAG, "Task [ " + this + " ] is submitting with param = " + this.getParam());
        TaskExecutor.getInstance().submit(this);
        return this;
    }

    @Override
    protected void doneExecution(TaskAbsResult result) {
    }

    @Override
    protected void onPreExecution() {

    }

    @Override
    public void cancel(boolean mayInterruptIfRunning) {
        if (!this.getTask().isDone() && !this.getTask().isCancelled()) {
//            MLog.verbose(ServiceConfig.MULTIPLE_TASK_TAG, "Task [ " + this + " ] is cancelling with param = " + this.getParam());
            this.getTask().cancel(mayInterruptIfRunning);
        }
    }

    @Override
    protected boolean retryExecution() {
        if (this.getParam().retryCount-- > 0) {
//            MLog.verbose(ServiceConfig.MULTIPLE_TASK_TAG, "Task [ " + this + " ] is retrying with param = " + this.getParam());
            TaskExecutor.getInstance().submit(this);
            return true;
        }
        return false;
    }
}
