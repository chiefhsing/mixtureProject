package com.xuwakao.mixture.utils;

import java.lang.reflect.Method;

/**
 * Created by xujiexing on 13-9-4.
 */
public class Utils {
    /**
     * A helper method to creat the return value of toString() method
     * @param cls
     * @param objects
     * @return
     */
    public static String makeToString(Class cls,Object... objects){
        StringBuilder builder = new StringBuilder(cls.getSimpleName());

        builder.append(buildParamString(objects));

        builder.append(Constants.LEFT_PARENTHESES);
        builder.append(cls.getSimpleName());
        builder.append(Constants.RIGHT_PARENTHESES);
        return builder.toString();
    }

    /**
     * make log message
     * @param cls
     * @param method
     * @param objects
     * @return
     */
    public static String makeLogMessage(Class cls, Method method,Object... objects){
        StringBuilder builder = new StringBuilder();
        builder.append("### ");
        builder.append(cls.getSimpleName());
        builder.append(".");
        builder.append(method.getName());
        builder.append(Constants.LEFT_PARENTHESES);
        builder.append(buildParamString(objects));
        builder.append(Constants.RIGHT_PARENTHESES);
        builder.append(" ###");
        return builder.toString();
    }

    public static String buildParamString(Object... objects){
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.LEFT_BRACKET);

        for(Object o : objects){
            if(o == null){
                builder.append(Constants.NULL_SYMBOL + Constants.COMMA_SYMBOL);
                continue;
            }
            builder.append(o.toString() + Constants.COMMA_SYMBOL);
        }

        builder = new StringBuilder(builder.substring(0, builder.length() - 2));
        builder.append(Constants.RIGHT_BRACKET);
        return builder.toString();
    }
}
