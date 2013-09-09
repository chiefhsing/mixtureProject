package com.xuwakao.mixture.framework.test;

import android.os.Looper;

import com.xuwakao.mixture.framework.httpmodule.HttpAbsRequestParam;
import com.xuwakao.mixture.framework.httpmodule.HttpAbsResult;
import com.xuwakao.mixture.framework.httpmodule.HttpBaseTask;
import com.xuwakao.mixture.framework.httpmodule.HttpServiceConfig;
import com.xuwakao.mixture.framework.utils.MLog;

/**
 * Created by xujiexing on 13-9-5.
 */
public class HttpTestTask extends HttpBaseTask {
    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    public HttpTestTask(HttpAbsRequestParam param, Looper looper) {
        super(param, looper);
    }

    @Override
    protected HttpAbsResult executeJob(final HttpAbsRequestParam mParams) throws Exception {
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "executeJob" + this + " before with mParams = " + mParams + " in thread = " + Thread.currentThread());
        final HttpResultBase result = new HttpResultBase();

        Thread.currentThread().sleep(3000);
        result.url = mParams.url;
        Thread.currentThread().sleep(6000);
        return result;
    }

    @Override
    protected void doneWithResult(HttpAbsResult result) {
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "doneWithResult " + this + " return reuslt = " + result);
    }
}
