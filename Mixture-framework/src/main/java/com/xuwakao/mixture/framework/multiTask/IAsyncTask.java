package com.xuwakao.mixture.framework.multiTask;

/**
 * Created by xujiexing on 13-9-23.
 */
public interface IAsyncTask {
    /**
     * Every task must be able to submit some computation to somewhere with specified executor
     *
     * @return The caller itself
     */
    public IAsyncTask submit();

    /**
     * Cancel this task
     *
     * @param mayInterruptIfRunning Whether force to cancel task even when the task is running now.
     *                              Neither set false nor true would the task return the result.
     *                              But set false, the job followed would be execute continue,otherwise,the
     *                              thread quit directly.
     */
    public void cancel(boolean mayInterruptIfRunning);
}
