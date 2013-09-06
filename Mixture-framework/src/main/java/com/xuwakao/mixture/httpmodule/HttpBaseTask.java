package com.xuwakao.mixture.httpmodule;

import android.os.Looper;

import com.xuwakao.mixture.utils.Utils;

/**
 * Created by xujiexing on 13-9-4.
 *
 * All the http task is strongly recommended to extend this base class.
 *
 */
public abstract class HttpBaseTask extends HttpAbsTaskWrapper implements HttpTaskInterface{

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
        HttpTaskWorker.getInstance().submit(this);
        return this;
    }

    @Override
    public void cancel(boolean mayInterruptIfRunning) {
        if(!this.getTask().isDone() && !this.getTask().isCancelled())
            this.getTask().cancel(mayInterruptIfRunning);
    }

    public static class HttpRequestParamBase extends HttpAbsRequestParam{
        @Override
        public String toString() {
            return Utils.makeToString(HttpRequestParamBase.class, new Object[]{url, priority, retryCount});
        }

        @Override
        public int compareTo(HttpAbsRequestParam another) {
            return this.priority.compareTo(another.priority);
        }
    }

    public static class HttpResultBase extends HttpAbsResult{
        @Override
        public String toString() {
            return Utils.makeToString(HttpResultBase.class,new Object[]{resultCode ,url, exception});
        }
    }
}
