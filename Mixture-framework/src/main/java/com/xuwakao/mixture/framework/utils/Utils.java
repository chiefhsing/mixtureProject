package com.xuwakao.mixture.framework.utils;

import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by xujiexing on 13-9-4.
 */
public class Utils {
    /**
     * A helper method to creat the return value of toString() method
     *
     * @param cls
     * @param objects
     * @return
     */
    public static String makeToString(Class cls, Object... objects) {
        StringBuilder builder = new StringBuilder();

        builder.append(buildParamString(objects));

        builder.append(Constants.LEFT_PARENTHESES);
        builder.append(cls.getSimpleName());
        builder.append(Constants.RIGHT_PARENTHESES);
        return builder.toString();
    }

    /**
     * make log message
     *
     * @param cls
     * @param method
     * @param objects
     * @return
     */
    public static String makeLogMessage(Class cls, Method method, Object... objects) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.TRIPLE_OCTOTHORPE + " ");
        builder.append(cls.getSimpleName());
        builder.append(Constants.DOT);
        builder.append(method.getName());
        builder.append(Constants.LEFT_PARENTHESES);
        builder.append(buildParamString(objects));
        builder.append(Constants.RIGHT_PARENTHESES);
        builder.append(" " + Constants.TRIPLE_OCTOTHORPE);
        return builder.toString();
    }

    public static String buildParamString(Object... objects) {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.LEFT_BRACKET);

        for (Object o : objects) {
            if (o == null) {
                builder.append(Constants.NULL_SYMBOL + Constants.COMMA_SYMBOL);
                continue;
            }
            builder.append(o.toString() + Constants.COMMA_SYMBOL);
        }

        builder = new StringBuilder(builder.substring(0, builder.length() - 2));
        builder.append(Constants.RIGHT_BRACKET);
        return builder.toString();
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
