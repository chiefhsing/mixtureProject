package com.xuwakao.mixture.framework.http;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpAbsResult {

    public enum HttpResultCode {
        SUCCESS, CANCELED, EXCEPTIONAL;
    }

    public HttpResultCode resultCode;
    public String url;
    public Exception exception;
}
