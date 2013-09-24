package com.xuwakao.mixture.framework.multiTask;

/**
 * Created by xujiexing on 13-9-24.
 */
public abstract class AbsTaskExecutor {

    /**
     * If the instance of the implementation of this class is singleton, this method's implementation be sycrhonized
     *
     * @param task
     */
    public abstract  void submit(AbsAysncFutureTaskWrapper task);

    public abstract void shutDown();

}
