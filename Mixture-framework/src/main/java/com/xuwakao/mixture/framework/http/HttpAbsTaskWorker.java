package com.xuwakao.mixture.framework.http;

import org.apache.http.HttpResponse;

/**
 * Created by duowan on 13-9-9.
 */
public interface HttpAbsTaskWorker {

    public HttpResponse executeRequest(String method, String url);

}
