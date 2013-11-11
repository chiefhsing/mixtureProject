package com.xuwakao.mixture.framework.multiTask;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xuwakao.mixture.framework.utils.Utils;

/**
 * Created by xujiexing on 13-9-23.
 * <p/>
 * A proxy implementing of {@link com.xuwakao.mixture.framework.multiTask.AbsAsyncFutureTask}
 */
public abstract class AbsAysncFutureTaskWrapper {
    protected static final int SUCCESS_MESSAGE = 0x1 << 0;
    protected static final int CANCELED_MESSAGE = 0x1 << 1;
    protected static final int EXCEPTIONAL_MESSAGE = 0x1 << 2;
    protected static final int TIMEOUT_MESSAGE = 0x1 << 3;

    /**
     * The mParam used to excute the http request
     */
    private TaskAbsRequestParam mParam;

    /**
     * The real mTask to be executed on
     */
    private AbsAsyncFutureTask mTask;

    private AbsAsyncFutureTask.AbsTaskRunnable<TaskAbsRequestParam, TaskAbsResult> mRunnable;

    private InternalHandler mInternalHandler;

    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this mTask
     */
    protected AbsAysncFutureTaskWrapper(final TaskAbsRequestParam param, Looper looper) {
        this.mParam = param;
        this.mInternalHandler = new InternalHandler(looper);
        this.mRunnable = new AbsAsyncFutureTask.AbsTaskRunnable<TaskAbsRequestParam, TaskAbsResult>() {
            @Override
            public TaskAbsResult call() throws Exception {
                this.mParam = param;
                onPreExecution();
                if (mTask != null && mTask.get() != null)
                    mTask.get().setState(AbsAsyncFutureTask.EXECUTING);
                TaskWatchDog.shareInstance().checkTimeOutTask(AbsAysncFutureTaskWrapper.this);
                return execute(param);
            }
        };

        this.mTask = new AbsAsyncFutureTask<TaskAbsRequestParam, TaskAbsResult>(this.mRunnable, this.mParam) {
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
        this.mRunnable.setTask(this.mTask);
    }

    protected boolean isCancelled() {
        return this.mTask.isCancelled();
    }

    /**
     * Post result
     *
     * @param what
     * @param object
     */
    public void postResult(int what, Object object) {
        if (mInternalHandler == null) {
            throw new IllegalStateException();
        }

        mInternalHandler.obtainMessage(what, object).sendToTarget();
    }


    /**
     * Return the mTask used to execute on.
     *
     * @return
     */
    public AbsAsyncFutureTask getTask() {
        return this.mTask;
    }

    /**
     * Return the mParam of the request used to execute the mTask
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
            TaskAbsResult result = null;

            int what = msg.what;
            switch (what) {
                case SUCCESS_MESSAGE:
                    result = (TaskAbsResult) msg.obj;
                    result.resultCode = TaskAbsResult.TaskResultCode.SUCCESS;
                    result.exception = null;
                    break;
                case CANCELED_MESSAGE:
                    if (AbsAysncFutureTaskWrapper.this.getTask().isShouldTimeoutRetry() && isShouldRetry()) {
                        retryExecution(AbsAysncFutureTaskWrapper.this);
                        return;
                    }

                    result = new TaskResultBase();
                    result.resultCode = TaskAbsResult.TaskResultCode.CANCELED;
                    result.exception = null;
                    break;
                case EXCEPTIONAL_MESSAGE:
                    if (isShouldRetry()) {
                        retryExecution(AbsAysncFutureTaskWrapper.this);
                        return;
                    }

                    result = new TaskResultBase();
                    result.resultCode = TaskAbsResult.TaskResultCode.EXCEPTIONAL;
                    result.exception = (Exception) msg.obj;
                default:
                    break;
            }

            AbsAysncFutureTaskWrapper.this.doneExecution(result);
        }
    }

    /**
     * Whether task should retry depends on the param field {@link TaskAbsRequestParam#retryCount}
     *
     * @return
     */
    private boolean isShouldRetry() {
        return this.getParam().retryCount-- > 0 ? true : false;
    }

    protected abstract TaskAbsResult execute(TaskAbsRequestParam mParams) throws Exception;

    protected abstract void doneExecution(TaskAbsResult result);

    protected abstract void onPreExecution();

    protected abstract void retryExecution(AbsAysncFutureTaskWrapper task);

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
