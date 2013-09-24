package com.xuwakao.mixture.framework.multiTask;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xuwakao.mixture.framework.ServiceConfig;
import com.xuwakao.mixture.framework.utils.MLog;
import com.xuwakao.mixture.framework.utils.Utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by xujiexing on 13-9-23.
 */
public abstract class AbsAysncFutureTaskWrapper {
    private static final int SUCCESS_MESSAGE = 0x1 << 1;
    private static final int CANCELED_MESSAGE = 0x1 << 2;
    private static final int EXCEPTIONAL_MESSAGE = 0x1 << 3;

    /**
     * The mParam used to excute the http request
     */
    private TaskAbsRequestParam mParam;

    /**
     * The real task to be executed on
     */
    private AbsAsyncFutureTask task;

    private AbsAsyncFutureTask.AbsTaskRunnable<TaskAbsRequestParam, TaskAbsResult> runnable;

    private InternalHandler internalHandler;

    private boolean completed = false;

    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    protected AbsAysncFutureTaskWrapper(TaskAbsRequestParam param, Looper looper) {
        this.mParam = param;
        this.internalHandler = new InternalHandler(looper);
        this.runnable = new AbsAsyncFutureTask.AbsTaskRunnable<TaskAbsRequestParam, TaskAbsResult>() {
            @Override
            public TaskAbsResult call() throws Exception {
                this.mParam = AbsAysncFutureTaskWrapper.this.mParam;

                return executeJob(this.mParam);
            }
        };

        this.task = new AbsAsyncFutureTask<TaskAbsRequestParam, TaskAbsResult>(this.runnable, this.mParam) {
            @Override
            protected void exceptionalJob(Exception e) {
                postResult(EXCEPTIONAL_MESSAGE, e);
            }

            @Override
            protected void successJob(TaskAbsResult result) {
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

        MLog.verbose(ServiceConfig.MULTIPLE_TASK_TAG, "### TaskWrapper postResult with what = " + what + ", object = " + object + " ###");
        internalHandler.obtainMessage(what, object).sendToTarget();
    }


    /**
     * Return the task used to execute on.
     *
     * @return
     */
    public AbsAsyncFutureTask getTask() {
        return this.task;
    }

    /**
     * Return the mParam of the request used to execute the task
     *
     * @return
     */
    public TaskAbsRequestParam getParam() {
        return this.mParam;
    }

    private class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            MLog.verbose(ServiceConfig.MULTIPLE_TASK_TAG, "### InternalHandler with msg = " + msg + " in thread = " + Thread.currentThread().getName() + " ###");
            TaskAbsResult result = null;

            int what = msg.what;
            switch (what) {
                case SUCCESS_MESSAGE:
                    result = (TaskAbsResult) msg.obj;
                    result.resultCode = TaskAbsResult.TaskResultCode.SUCCESS;
                    result.exception = null;
                    break;
                case CANCELED_MESSAGE:
                    result = new TaskResultBase();
                    result.resultCode = TaskAbsResult.TaskResultCode.CANCELED;
                    result.exception = null;
                    break;
                case EXCEPTIONAL_MESSAGE:
                    if (AbsAysncFutureTaskWrapper.this.retryJob()) {
                        return;
                    }

                    result = new TaskResultBase();
                    result.resultCode = TaskAbsResult.TaskResultCode.EXCEPTIONAL;
                    result.exception = (Exception) msg.obj;
                default:
                    break;
            }

            AbsAysncFutureTaskWrapper.this.doneWithResult(result);
        }
    }

    protected abstract TaskAbsResult executeJob(TaskAbsRequestParam mParams) throws Exception;

    protected abstract void doneWithResult(TaskAbsResult result);

    protected abstract boolean retryJob();

    public static class TaskRequestParamBase extends TaskAbsRequestParam {

        public TaskRequestParamBase() {
            this.priority = TaskPriority.DEFAULT;
            this.timeout = Integer.MAX_VALUE;
        }

        @Override
        public String toString() {
            return Utils.makeToString(TaskRequestParamBase.class, new Object[]{priority, retryCount});
        }

        @Override
        public int compareTo(TaskAbsRequestParam another) {
            return this.priority.compareTo(another.priority);
        }
    }

    public static class TaskResultBase extends TaskAbsResult {
        @Override
        public String toString() {
            return Utils.makeToString(TaskAbsResult.class, new Object[]{resultCode, exception});
        }
    }

}
