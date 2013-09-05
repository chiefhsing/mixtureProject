package com.xuwakao.mixture.httpmodule;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsTaskWrapper{
    private static final String TAG = "HttpAbsTaskWrapper";

    /**
     * The mParam used to excute the http request
     */
    private HttpAbsRequestParam mParam;

    /**
     *
     */
    private HttpAbstractTask task;

    private HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult> runnable;

    protected HttpAbsTaskWrapper(final HttpAbsRequestParam param){
        this.mParam = param;
        this.runnable = new HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult>() {
            @Override
            public HttpAbsResult call() throws Exception {
                this.mParam = HttpAbsTaskWrapper.this.mParam;
                return executeJob(this.mParam);
            }
        };

        this.task = new HttpAbstractTask<HttpAbsResult>((Callable<HttpAbsResult>) this.runnable) {

            @Override
            protected void success(HttpAbsResult result) {
                Log.v(TAG, "### HttpAbsTaskWrapper success with result = " + result + ", mParam = " + HttpAbsTaskWrapper.this.mParam + " ###");
                HttpAbsTaskWrapper.this.success(result);
            }

            @Override
            protected void exceptional(Exception e) {
                Log.v(TAG, "### HttpAbsTaskWrapper exceptional with Exception = " + e.getMessage() + ", mParam = " + HttpAbsTaskWrapper.this.mParam + " ###");
                HttpAbsTaskWrapper.this.exceptional(e);
            }

            @Override
            protected void canceled() {
                Log.v(TAG, "### HttpAbsTaskWrapper cancel with mParam = " + HttpAbsTaskWrapper.this.mParam + "###");
                HttpAbsTaskWrapper.this.canceled();
            }
        };

    }

    /**
     * Return the task used to execute on.
     * @return
     */
    public HttpAbstractTask getTask(){
        return this.task;
    }

    /**
     * Return the mParam of the request used to execute the task
     * @return
     */
    public HttpAbsRequestParam getParam(){
        return this.mParam;
    }

    protected abstract HttpAbsResult executeJob(HttpAbsRequestParam mParams);
    protected abstract void success(HttpAbsResult result);
    protected abstract void canceled();
    protected abstract void exceptional(Exception e);
}
