package com.xuwakao.mixture.httpmodule;

import android.util.Log;

import com.xuwakao.mixture.utils.Utils;

import java.util.concurrent.ExecutionException;

/**
 * Created by xujiexing on 13-9-4.
 */
public abstract class HttpBaseTask extends HttpAbsTaskWrapper implements HttpTaskInterface{

    protected HttpBaseTask(HttpAbsRequestParam param) {
        super(param);
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

    public static class HttpRequestParamBase extends HttpAbsRequestParam implements Comparable<HttpWorkPriority>{
        @Override
        public String toString() {
            return Utils.makeToString(HttpRequestParamBase.class, new Object[]{url, priority, retryCount});
        }

        @Override
        public int compareTo(HttpWorkPriority another) {
            return this.priority.compareTo(another);
        }
    }

    public static class HttpResultBase extends HttpAbsResult{
        @Override
        public String toString() {
            return Utils.makeToString(HttpResultBase.class,new Object[]{resultCode ,url, exception});
        }
    }
}
