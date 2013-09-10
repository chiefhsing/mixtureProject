package com.xuwakao.mixture.framework.http;

/**
 * Created by xujiexing on 13-9-4.
 */
public interface HttpTaskInterface {

    /**
     * Every task must be able to submit some computation to somewhere
     *
     * @return The caller itself
     */
    public HttpTaskInterface submit();

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
