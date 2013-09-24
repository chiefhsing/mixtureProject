package com.xuwakao.mixture.framework.http;

import com.xuwakao.mixture.framework.multiTask.AbsAysncFutureTaskWrapper;
import com.xuwakao.mixture.framework.multiTask.TaskPriority;

/**
 * Created by xujiexing on 13-9-23.
 */
public class HttpTaskRequestParam extends AbsAysncFutureTaskWrapper.TaskRequestParamBase{
    public String url;

    public HttpTaskRequestParam(){
        this.timeout = 15;
        this.retryCount = 0;
        this.priority = TaskPriority.DEFAULT;
    }
}
