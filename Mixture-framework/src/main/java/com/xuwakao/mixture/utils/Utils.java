package com.xuwakao.mixture.utils;

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
        builder.append(Constants.LEFT_PARENTHESES);
        builder.append(cls.getSimpleName());
        builder.append(Constants.RIGHT_PARENTHESES);
        return builder.toString();
    }
}
