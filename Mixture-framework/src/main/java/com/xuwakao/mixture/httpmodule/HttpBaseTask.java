package com.xuwakao.mixture.httpmodule;

import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by xujiexing on 13-9-4.
 */
public class HttpBaseTask extends HttpAbsTaskWrapper{

    public HttpBaseTask(HttpAbsRequestParam param) {
        super(param);
    }

    @Override
    protected HttpAbsResult executeJob(HttpAbsRequestParam mParams) {
        Log.v("HttpBaseTask", "HttpBaseTask is excuting some job");
        return new HttpAbsResult() {
            @Override
            public String toString() {
                return "HttpAbsResult return";
            }
        };
    }

    @Override
    protected void doneJob() {
        try {
            HttpAbsResult result = (HttpAbsResult) getTask().get();
            Log.v("HttpBaseTask", result.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Every task must be able to submit some computation to somewhere
     *
     * @return The caller itself
     */
    @Override
    public HttpTaskInterface submit() {
        HttpTaskWorker.getInstance().submit(this);
        return this;
    }
}
