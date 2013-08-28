package com.xuwakao.mixture.Utils;

import android.util.Log;

import com.xuwakao.mixture.ServiceConfig;

/**
 * Created by xuwakao on 13-8-28.
 */
public class MLog {
    private static final int MAX_LOG_TAG_LENGTH = 25;
    private static final String LOG_PREFIX = "mixture_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();


    /**
     * common function for making log tag
     * @param str
     * @return
     */
    public static String makeLogTag(String str){
        if(str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH){
            return LOG_PREFIX + str.substring(0,MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * WARNING: Don't use this when obfuscating class names with Proguard!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    /**
     * print verbose level log,before logging it's strongly recommoned check whether isLoggable
     * @param tag
     * @param msg
     */
    public static void verbose(String tag, String msg){
        if(ServiceConfig.getInstance().isDebuggable()){
            Log.i(tag,msg);
        }
    }

    /**
     *print verbose level log,before logging it's strongly recommoned check whether isLoggable
     * @param tag
     * @param msg
     * @param tr
     */
    public static void verbose(String tag, String msg, Throwable tr){
        if(ServiceConfig.getInstance().isDebuggable()){
            Log.i(tag, msg, tr);
        }
    }

    /**
     * print debug level log,before logging it's strongly recommoned check whether isLoggable
     * @param tag
     * @param msg
     */
    public static void debug(String tag, String msg){
        if(ServiceConfig.getInstance().isDebuggable()){
            Log.d(tag, msg);
        }
    }

    /**
     * print debug level log,before logging it's strongly recommoned check whether isLoggable
     * @param tag
     * @param msg
     * @param tr
     */
    public static void debug(String tag, String msg, Throwable tr){
        if(ServiceConfig.getInstance().isDebuggable()){
            Log.d(tag, msg, tr);
        }
    }

    /**
     *
     * @param tag
     * @param msg
     */
    public static void info(String tag, String msg){
        Log.i(tag, msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void info(String tag, String msg, Throwable tr){
        Log.i(tag, msg, tr);
    }

    /**
     *
     * @param tag
     * @param msg
     */
    public static void warn(String tag, String msg){
        Log.w(tag, msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void warn(String tag, String msg, Throwable tr){
        Log.w(tag, msg, tr);
    }

    /**
     *
     * @param tag
     * @param msg
     */
    public static void error(String tag, String msg){
        Log.e(tag, msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void error(String tag, String msg, Throwable tr){
        Log.w(tag, msg, tr);
    }
}
