package com.xuwakao.mixture.framework;

import android.content.res.Configuration;

/**
 * Created by xuwakao on 13-8-28.
 */
public class ServiceConfig {
    private boolean debuggable = false;
    public static final String LOG_FILE_NAME = "log.txt";

    public static final String EXTERNAL_DIR = "xuwakao";

    public static final String MULTIPLE_TASK_TAG = "multiple_task";
    public static final int EXECUTOR_CORE_POOL_MIN_SIZE = 5;
    public static final int EXECUTOR_POOL_MAX_SIZE = 5;
    public static final long EXCESS_THREAD_KEEP_ALIVE_TIME = 10;
    public static final int EXECUTOR_QUEUE_INITIALIZED_CAPACITY = 8;

    private static ServiceConfig config;
    private Configuration configuration;

    private ServiceConfig() {

    }

    public static ServiceConfig getInstance() {
        if (config == null) {
            config = new ServiceConfig();
        }

        return config;
    }

    /**
     * set the debuggable
     *
     * @param debuggable
     */
    public void setDebuggable(boolean debuggable) {
        this.debuggable = debuggable;
    }

    /**
     * @return debuggable
     */
    public boolean isDebuggable() {
        return this.debuggable;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}