package com.xuwakao.mixture.framework.httpmodule;

/**
 * Created by xujiexing on 13-9-3.
 */

import java.util.Comparator;

/**
 * The priority of the http worker.
 *
 * The larger the {@link #mValue}, the higher the proority.
 */
public class HttpWorkPriority implements Comparable<HttpWorkPriority>{

    private static final int LOW_VALUE = 0x1 << 0;
    private static final int DEFAULT_VALUE = 0x1 << 1;
    private static final int HIGH_VALUE = 0x1 << 2;
    private static final int URGENCY_VALUE = 0x1 << 3;

    public static final HttpWorkPriority LOW = new HttpWorkPriority(LOW_VALUE);

    public static final HttpWorkPriority DEFAULT = new HttpWorkPriority(DEFAULT_VALUE);

    public static final HttpWorkPriority HIGH = new HttpWorkPriority(HIGH_VALUE);

    public static final HttpWorkPriority URGENCY = new HttpWorkPriority(URGENCY_VALUE);

    private int mValue;

    /**
     * Construtor with {@link #DEFAULT}
     */
    private HttpWorkPriority(){
        mValue = DEFAULT.getValue();
    }

    /**
     * Construtor with speecied priority
     *
     * @param value Priority value
     */
    private HttpWorkPriority(int value){
        mValue = value;
    }

    public Integer getValue(){
        return mValue;
    }

    @Override
    public String toString() {
        return String.valueOf(mValue);
    }

    @Override
    public int compareTo(HttpWorkPriority o){
        return getValue().compareTo(o.getValue());
    }

    /**
     * Inverted order Comparator.
     *
     * @param <T>
     */
    public static class InvertedComparator<T extends Comparable> implements Comparator<T>{
        @Override
        public int compare(T lhs, T rhs) {
            return -lhs.compareTo(rhs);
        }
    }
}
