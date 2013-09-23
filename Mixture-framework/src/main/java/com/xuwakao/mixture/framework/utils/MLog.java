package com.xuwakao.mixture.framework.utils;

import android.util.Log;

import com.xuwakao.mixture.framework.AppConfig;

/**
 * Created by xuwakao on 13-8-28.
 */
public class MLog {
    private static final int MAX_LOG_TAG_LENGTH = 25;
    private static final String LOG_PREFIX = "mixture_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();


    /**
     * common function for making log tag
     *
     * @param str
     * @return
     */
    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
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
     *
     * @param tag
     * @param msg
     */
    public static void verbose(String tag, String msg) {
        if (AppConfig.getInstance().isDebuggable()) {
            Log.i(tag, msg);
        }
    }

    /**
     * print verbose level log,before logging it's strongly recommoned check whether isLoggable
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void verbose(String tag, String msg, Throwable tr) {
        if (AppConfig.getInstance().isDebuggable()) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * print debug level log,before logging it's strongly recommoned check whether isLoggable
     *
     * @param tag
     * @param msg
     */
    public static void debug(String tag, String msg) {
        if (AppConfig.getInstance().isDebuggable()) {
            Log.d(tag, msg);
        }
    }

    /**
     * print debug level log,before logging it's strongly recommoned check whether isLoggable
     *
     * @param tag
     * @param msg
     * @param tr
     */
    public static void debug(String tag, String msg, Throwable tr) {
        if (AppConfig.getInstance().isDebuggable()) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * @param tag
     * @param msg
     */
    public static void info(String tag, String msg) {
        Log.i(tag, msg);
    }

    /**
     * @param tag
     * @param msg
     * @param tr
     */
    public static void info(String tag, String msg, Throwable tr) {
        Log.i(tag, msg, tr);
    }

    /**
     * @param tag
     * @param msg
     */
    public static void warn(String tag, String msg) {
        Log.w(tag, msg);
    }

    /**
     * @param tag
     * @param msg
     * @param tr
     */
    public static void warn(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    /**
     * @param tag
     * @param msg
     */
    public static void error(String tag, String msg) {
        Log.e(tag, msg);
    }


    /**
     * @param tag
     * @param msg
     * @param tr
     */
    public static void error(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    private static void writeToLog(String tag, String msg) {

    }

    private static String logFileString(String tag, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(msg);
        sb.append("(PID:");
        sb.append(android.os.Process.myPid());
        sb.append(")");
        sb.append("(TID:");
        sb.append(Thread.currentThread().getId());
        sb.append(")");
        sb.append("(TAG:");
        sb.append(tag);
        sb.append(")");
        sb.append("at (");
        sb.append(getCallerFilename());
        sb.append(":");
        sb.append(getCallerMethodName());
        sb.append(":");
        sb.append(getCallerLineNumber());
        sb.append(")");
        String ret = sb.toString();
        return ret;
    }

    /**
     * get the line number on where some method call current method
     *
     * @return line number
     */
    private static int getCallerLineNumber() {
        return Thread.currentThread().getStackTrace()[4].getLineNumber();
    }


    /**
     * get the file name of who is the caller
     *
     * @return file name
     */
    private static String getCallerFilename() {
        return Thread.currentThread().getStackTrace()[4].getFileName();
    }


    /**
     * get the name of method who call current method
     *
     * @return
     */
    private static String getCallerMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }
}
