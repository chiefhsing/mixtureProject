package com.xuwakao.mixture.framework.http;

import android.os.Looper;

/**
 * Created by xujiexing on 13-9-4.
 * <p/>
 * All the http task is strongly recommended to extend this base class.
 */
public abstract class HttpBaseTask extends HttpAbsTaskWrapper implements HttpTaskInterface {

    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    protected HttpBaseTask(HttpAbsRequestParam param, Looper looper) {
        super(param, looper);
    }

    /**
     * Every task must be able to submit some computation to somewhere
     *
     * @return The caller itself
     */
    @Override
    public HttpTaskInterface submit() {
        HttpTaskDispatcher.getInstance().submit(this);
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
            HttpTaskDispatcher.getInstance().submit(this);
            return true;
        }
        return false;
    }

    @Deprecated
    @Override
    protected void successJob(HttpAbsResult result) {
    }

    @Deprecated
    @Override
    protected void canceledJob() {
    }

    @Deprecated
    @Override
    protected void exceptionalJob(Exception e) {
    }
}
