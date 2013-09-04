package com.xuwakao.mixture.httpmodule;

/**
 * Created by xujiexing on 13-9-3.
 */
/**
 * The priority of the http worker.
 *
 * The larger the {@link #mValue}, the higher the proority.
 */
public class HttpWorkPriority implements Comparable<HttpWorkPriority>{

    public static final HttpWorkPriority LOW = new HttpWorkPriority(1);

    public static final HttpWorkPriority DEFAULT = new HttpWorkPriority(2);

    public static final HttpWorkPriority HIGH = new HttpWorkPriority(4);

    public static final HttpWorkPriority URGENCY = new HttpWorkPriority(8);

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
}
