package com.xuwakao.mixture.framework.multiTask;

/**
 * Created by xujiexing on 13-9-23.
 */
public abstract class TaskAbsResult {
    public enum TaskResultCode {
        SUCCESS, CANCELED, EXCEPTIONAL;
    }

    public TaskResultCode resultCode;
    public Exception exception;
}
