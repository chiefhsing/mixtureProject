package com.xuwakao.mixture.framework.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xuwakao.mixture.framework.utils.MLog;
import com.xuwakao.mixture.framework.utils.Utils;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsTaskWrapper {
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
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    protected HttpAbsTaskWrapper(HttpAbsRequestParam param, Looper looper) {
        this.mParam = param;
        this.internalHandler = new InternalHandler(looper);
        this.runnable = new HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult>() {
            @Override
            public HttpAbsResult call() throws Exception {
                this.mParam = HttpAbsTaskWrapper.this.mParam;
                return executeJob(this.mParam);
            }
        };

        this.task = new HttpAbstractTask<HttpAbsRequestParam, HttpAbsResult>(this.runnable, this.mParam) {
            @Override
            protected void exceptionalJob(Exception e) {
                postResult(EXCEPTIONAL_MESSAGE, e);
            }

            @Override
            protected void successJob(HttpAbsResult result) {
                postResult(SUCCESS_MESSAGE, result);
            }

            @Override
            protected void canceledJob() {
                postResult(CANCELED_MESSAGE, null);
            }
        };

    }

    /**
     * Post result
     *
     * @param what
     * @param object
     */
    public void postResult(int what, Object object) {
        if (internalHandler == null) {
            throw new IllegalStateException();
        }

        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "### HttpAbsTaskWrapper postResult with what = " + what + ", object = " + object + " ###");
        internalHandler.obtainMessage(what, object).sendToTarget();
    }

    /**
     * Return the task used to execute on.
     *
     * @return
     */
    public HttpAbstractTask getTask() {
        return this.task;
    }

    /**
     * Return the mParam of the request used to execute the task
     *
     * @return
     */
    public HttpAbsRequestParam getParam() {
        return this.mParam;
    }

    private class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "### InternalHandler with msg = " + msg + " in thread = " + Thread.currentThread().getName() + " ###");
            HttpAbsResult result = null;

            int what = msg.what;
            switch (what) {
                case SUCCESS_MESSAGE:
                    result = (HttpAbsResult) msg.obj;
                    result.resultCode = HttpAbsResult.HttpResultCode.SUCCESS;
                    result.exception = null;
                    break;
                case CANCELED_MESSAGE:
                    result = new HttpBaseTask.HttpResultBase();
                    result.resultCode = HttpAbsResult.HttpResultCode.CANCELED;
                    result.exception = null;
                    break;
                case EXCEPTIONAL_MESSAGE:
                    if (HttpAbsTaskWrapper.this.retryJob()) {
                        return;
                    }

                    result = new HttpBaseTask.HttpResultBase();
                    result.resultCode = HttpAbsResult.HttpResultCode.EXCEPTIONAL;
                    result.exception = (Exception) msg.obj;
                default:
                    break;
            }
            result.url = getParam().url;
            HttpAbsTaskWrapper.this.doneWithResult(result);
        }
    }

    @Override
    public String toString() {
        return this.task.toString();
    }

    protected abstract HttpAbsResult executeJob(HttpAbsRequestParam mParams) throws Exception;

    protected abstract void doneWithResult(HttpAbsResult result);

    protected abstract boolean retryJob();

    @Deprecated
    protected abstract void successJob(HttpAbsResult result);

    @Deprecated
    protected abstract void canceledJob();

    /**
     * There is no need to retry job in this method.
     * <p/>
     * Whether job is attempted to retry or not,it is based on the attribute {@link com.xuwakao.mixture.framework.http.HttpAbsRequestParam#retryCount}
     * and the http module has ability to handle retrying job internally.
     * This method is used to handle exception,for example,give some UI response to users and so on.
     * You can customize retryJob by subclassing this class,like {@link HttpBaseTask#retryJob()}.
     * But it's strongly recommended to subclass {@link com.xuwakao.mixture.framework.http.HttpBaseTask},but not this.
     *
     * @param e
     */
    @Deprecated
    protected abstract void exceptionalJob(Exception e);

    public static class HttpRequestParamBase extends HttpAbsRequestParam {

        public HttpRequestParamBase(){
            this.priority = HttpWorkPriority.DEFAULT;
        }

        @Override
        public String toString() {
            return Utils.makeToString(HttpRequestParamBase.class, new Object[]{url, priority, retryCount});
        }

        @Override
        public int compareTo(HttpAbsRequestParam another) {
            return this.priority.compareTo(another.priority);
        }
    }

    public static class HttpResultBase extends HttpAbsResult {
        @Override
        public String toString() {
            return Utils.makeToString(HttpResultBase.class, new Object[]{resultCode, url, exception});
        }
    }
}
