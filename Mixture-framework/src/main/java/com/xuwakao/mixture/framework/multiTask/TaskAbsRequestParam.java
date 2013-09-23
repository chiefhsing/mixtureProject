package com.xuwakao.mixture.framework.multiTask;

/**
 * Created by xujiexing on 13-9-23.
 */
public abstract class TaskAbsRequestParam implements Comparable<TaskAbsRequestParam> {
    /**
     * The priority of the job
     */
    public TaskPriority priority;

    /**
     * The amount of retrial
     */
    public int retryCount;
}
