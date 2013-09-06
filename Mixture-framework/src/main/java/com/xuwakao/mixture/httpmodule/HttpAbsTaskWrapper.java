package com.xuwakao.mixture.httpmodule;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsTaskWrapper{
    private static final int SUCCESS_MESSAGE = 0x1 << 1;
    private static final int CANCELED_MESSAGE = 0x1 << 2;
    private static final int EXCEPTIONAL_MESSAGE = 0x1 << 3;

    /**
     * The mParam used to excute the http request
     */
    private HttpAbsRequestParam mParam;

    /**
     * The real task to be executed on
     */
    private HttpAbstractTask task;

    private HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult> runnable;

    private InternalHandler internalHandler;

    /**
     *
     * Constructor
     *
     * @param param The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    protected HttpAbsTaskWrapper(HttpAbsRequestParam param,Looper looper){
        this.mParam = param;
        this.internalHandler = new InternalHandler(looper);
        this.runnable = new HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult>() {
            @Override
            public HttpAbsResult call() throws Exception{
                this.mParam = HttpAbsTaskWrapper.this.mParam;
                return executeJob(this.mParam);
            }
        };

        this.task = new HttpAbstractTask<HttpAbsRequestParam,HttpAbsResult>(this.runnable,this.mParam) {
            @Override
            protected void exceptionalJob(Exception e) {
                postReuslt(EXCEPTIONAL_MESSAGE, e);
            }

            @Override
            protected void successJob(HttpAbsResult result) {
                postReuslt(SUCCESS_MESSAGE, result);
            }

            @Override
            protected void canceledJob() {
                postReuslt(CANCELED_MESSAGE, null);
            }
        };

    }

    /**
     * Post result
     * @param what
     * @param object
     */
    public void postReuslt(int what, Object object){
        if(internalHandler == null){
            throw new IllegalStateException();
        }

        Log.v(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbsTaskWrapper postResult with what = " + what + ", object = " + object + " ###");
        internalHandler.obtainMessage(what, object).sendToTarget();
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

    private class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.v(HttpServiceConfig.HTTP_TASK_TAG, "### InternalHandler with msg = " + msg + " in thread = " + Thread.currentThread().getName() +" ###");

            int what = msg.what;
            switch (what){
                case SUCCESS_MESSAGE:
                    HttpAbsTaskWrapper.this.successJob((HttpAbsResult) msg.obj);
                    break;
                case CANCELED_MESSAGE:
                    HttpAbsTaskWrapper.this.canceledJob();
                    break;
                case EXCEPTIONAL_MESSAGE:
                    HttpAbsTaskWrapper.this.exceptionalJob((Exception) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return this.task.toString();
    }

    protected abstract  HttpAbsResult executeJob(HttpAbsRequestParam mParams) throws Exception;
    protected abstract void successJob(HttpAbsResult result);
    protected abstract void canceledJob();
    protected abstract void exceptionalJob(Exception e);
}
