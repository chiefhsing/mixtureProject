package com.xuwakao.mixture.test;

import android.os.Looper;
import android.util.Log;

import com.xuwakao.mixture.httpmodule.HttpAbsRequestParam;
import com.xuwakao.mixture.httpmodule.HttpAbsResult;
import com.xuwakao.mixture.httpmodule.HttpBaseTask;
import com.xuwakao.mixture.httpmodule.HttpServiceConfig;

/**
 * Created by xujiexing on 13-9-5.
 */
public class HttpTestTask extends HttpBaseTask {
    private Object lock = new Object();

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
    protected HttpAbsResult executeJob(HttpAbsRequestParam mParams) throws Exception {
        Log.v(HttpServiceConfig.HTTP_TASK_TAG, "before executeJob withe mParams = " + mParams);
        HttpResultBase result = null;
        synchronized (lock){
            lock.wait(1000);
            result = new HttpResultBase();
            result.url = mParams.url;
            result.resultCode = HttpAbsResult.HttpResultCode.EXCEPTIONAL;
            result.exception = new InterruptedException("xuwakao request fails");
            lock.wait(10000);
        }

        Log.v(HttpServiceConfig.HTTP_TASK_TAG, "after executeJob with mParams = " + mParams);
        return result;
    }

    @Override
    protected void successJob(HttpAbsResult result) {
        Log.v(HttpServiceConfig.HTTP_TASK_TAG, "successJob with result = " + result);
    }

    @Override
    protected void canceledJob() {
        Log.v(HttpServiceConfig.HTTP_TASK_TAG, "canceledJob");
    }

    @Override
    protected void exceptionalJob(Exception e) {
        Log.v(HttpServiceConfig.HTTP_TASK_TAG, "exceptionalJob with Exception = " + e.getMessage());
    }
}
