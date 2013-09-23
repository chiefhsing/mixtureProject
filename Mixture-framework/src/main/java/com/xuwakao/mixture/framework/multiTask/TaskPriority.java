package com.xuwakao.mixture.framework.multiTask;

import java.util.Comparator;

/**
 * Created by xujiexing on 13-9-23.
 *
 * The priority of task
 */
public class TaskPriority implements Comparable<TaskPriority> {
    private static final int LOW_VALUE = 0x1 << 0;
    private static final int DEFAULT_VALUE = 0x1 << 1;
    private static final int HIGH_VALUE = 0x1 << 2;
    private static final int URGENCY_VALUE = 0x1 << 3;

    public static final TaskPriority LOW = new TaskPriority(LOW_VALUE);

    public static final TaskPriority DEFAULT = new TaskPriority(DEFAULT_VALUE);

    public static final TaskPriority HIGH = new TaskPriority(HIGH_VALUE);

    public static final TaskPriority URGENCY = new TaskPriority(URGENCY_VALUE);

    private int mValue;

    /**
     * Construtor with {@link #DEFAULT}
     */
    private TaskPriority() {
        mValue = DEFAULT.getValue();
    }

    /**
     * Construtor with specified priority
     *
     * @param value Priority value.
     *
     * See {@link #LOW_VALUE},{@link #DEFAULT_VALUE},{@link #HIGH_VALUE},{@link #URGENCY_VALUE}
     */
    private TaskPriority(int value) {
        mValue = value;
    }

    public Integer getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return String.valueOf(mValue);
    }

    @Override
    public int compareTo(TaskPriority o) {
        return getValue().compareTo(o.getValue());
    }

    /**
     * Inverted order Comparator.
     *
     * @param <T>
     */
    public static class InvertedComparator<T extends Comparable> implements Comparator<T> {
        @Override
        public int compare(T lhs, T rhs) {
            return -lhs.compareTo(rhs);
        }
    }
}
