package com.xuwakao.mixture.test;

import android.os.Looper;

import com.xuwakao.mixture.framework.multiTask.AbsBaseAsyncTask;
import com.xuwakao.mixture.framework.multiTask.TaskAbsRequestParam;
import com.xuwakao.mixture.framework.multiTask.TaskAbsResult;

/**
 * Created by duowan on 13-11-1.
 */
public class TestMultipleTask  extends AbsBaseAsyncTask {
    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    protected TestMultipleTask(TaskRequestParamBase param, Looper looper) {
        super(param, looper);
    }

    @Override
    protected TaskAbsResult execute(TaskAbsRequestParam mParams) throws Exception {
        synchronized (Thread.currentThread()){
            Thread.currentThread().wait(5000);
        }
        return null;
    }
}
