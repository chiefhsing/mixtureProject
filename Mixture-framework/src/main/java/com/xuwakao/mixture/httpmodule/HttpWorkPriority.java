package com.xuwakao.mixture.httpmodule;

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

    private HttpWorkPriority(){
        mValue = DEFAULT.getValue();
    }

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

    public static class InvertedComparator<T extends Comparable> implements Comparator<T>{

        /**
         * Compares the two specified objects to determine their relative ordering. The ordering
         * implied by the return value of this method for all possible pairs of
         * {@code (lhs, rhs)} should form an <i>equivalence relation</i>.
         * This means that
         * <ul>
         * <li>{@code compare(a, a)} returns zero for all {@code a}</li>
         * <li>the sign of {@code compare(a, b)} must be the opposite of the sign of {@code
         * compare(b, a)} for all pairs of (a,b)</li>
         * <li>From {@code compare(a, b) > 0} and {@code compare(b, c) > 0} it must
         * follow {@code compare(a, c) > 0} for all possible combinations of {@code
         * (a, b, c)}</li>
         * </ul>
         *
         * @param lhs an {@code Object}.
         * @param rhs a second {@code Object} to compare with {@code lhs}.
         * @return an integer < 0 if {@code lhs} is less than {@code rhs}, 0 if they are
         * equal, and > 0 if {@code lhs} is greater than {@code rhs}.
         * @throws ClassCastException if objects are not of the correct type.
         */
        @Override
        public int compare(T lhs, T rhs) {
            return -lhs.compareTo(rhs);
        }
    }
}
