package com.xuwakao.mixture.httpmodule;

/**
 * Created by xujiexing on 13-9-4.
 */
public interface HttpTaskInterface {

    /**
     * Every task must be able to submit some computation to somewhere
     *
     * @return The caller itself
     */
    public HttpTaskInterface submit();

}
