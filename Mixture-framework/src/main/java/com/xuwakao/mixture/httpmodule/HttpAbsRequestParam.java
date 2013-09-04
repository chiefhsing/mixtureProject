package com.xuwakao.mixture.httpmodule;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsRequestParam {

    /**
     * The url related to this worker
     */
    public static String url;

    /**
     * The priority of the job
     */
    public static HttpWorkPriority priority;

    /**
     * The amount of retrial
     */
    public static int retryCount;
}
