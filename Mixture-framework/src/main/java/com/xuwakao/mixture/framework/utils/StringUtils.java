package com.xuwakao.mixture.framework.utils;

/**
 * Created by xujiexing on 13-10-10.
 */
public class StringUtils {
    public static boolean isEmptyOrNull(String string){
        return (string == null || string.length() <= 0) ? true : false;
    }
}
