package com.xuwakao.mixture.httpmodule;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsTaskWrapper implements HttpTaskInterface{

    /**
     * The param used to excute the http request
     */
    public HttpAbsRequestParam param;

    /**
     *
     */
    private HttpAbstractTask task;

    private HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult> runnable;

    public HttpAbsTaskWrapper(HttpAbsRequestParam param){
        this.param = param;
        this.runnable = new HttpAbstractTask.HttpTaskRunnable<HttpAbsRequestParam, HttpAbsResult>() {
            @Override
            public HttpAbsResult call() throws Exception {
                this.mParams = HttpAbsTaskWrapper.this.param;

                return executeJob(this.mParams);
            }
        };

        this.task = new HttpAbstractTask(this.runnable) {
            /**
             * Protected method invoked when this task transitions to state
             * {@code isDone} (whether normally or via cancellation). The
             * default implementation does nothing.  Subclasses may override
             * this method to invoke completion callbacks or perform
             * bookkeeping. Note that you can query status inside the
             * implementation of this method to determine whether this task
             * has been cancelled.
             */
            @Override
            protected void done() {
                super.done();
                doneJob();
            }
        };

    }

    public HttpAbstractTask getTask(){
        return this.task;
    }

    protected abstract HttpAbsResult executeJob(HttpAbsRequestParam mParams);
    protected abstract void doneJob();
}
