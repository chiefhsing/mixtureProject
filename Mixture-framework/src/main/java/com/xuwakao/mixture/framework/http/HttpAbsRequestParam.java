package com.xuwakao.mixture.framework.http;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsRequestParam implements Comparable<HttpAbsRequestParam> {

    /**
     * The url related to this worker
     */
    public String url;

    /**
     * The priority of the job
     */
    public HttpWorkPriority priority;

    /**
     * The amount of retrial
     */
    public int retryCount;
}
