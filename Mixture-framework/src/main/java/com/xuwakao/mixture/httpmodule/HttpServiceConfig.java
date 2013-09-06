package com.xuwakao.mixture.httpmodule;

/**
 * Created by xujiexing on 13-9-4.
 */
public class HttpServiceConfig {
    public static final int EXECUTOR_CORE_POOL_MIN_SIZE = 5;
    public static final int EXECUTOR_POOL_MAX_SIZE = 5;
    public static final long EXCESS_THREAD_KEEP_ALIVE_TIME = 10;

    public static final String HTTP_TASK_TAG = "HttpTask";
}
