package com.xuwakao.mixture.framework;

/**
 * Created by xuwakao on 13-8-28.
 */
public class ServiceConfig {
    public static final String MULTIPLE_TASK_TAG = "multiple_task";
    public static final int EXECUTOR_CORE_POOL_MIN_SIZE = 5;
    public static final int EXECUTOR_POOL_MAX_SIZE = 5;
    public static final long EXCESS_THREAD_KEEP_ALIVE_TIME = 10;
    public static final int EXECUTOR_QUEUE_INITIALIZED_CAPACITY = 8;

    private static ServiceConfig config;

    private ServiceConfig() {

    }

    public static ServiceConfig getInstance() {
        if (config == null) {
            config = new ServiceConfig();
        }

        return config;
    }
}