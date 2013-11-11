package com.xuwakao.mixture.framework;

import android.content.Context;
import android.content.res.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuwakao on 13-8-28.
 */
public class AppConfig {
    private boolean debuggable = false;
    public static final String LOG_FILE_NAME = "log.txt";

    public static final String EXTERNAL_DIR = "xuwakao";

    public static AtomicInteger loadImage = new AtomicInteger(0);
    public static AtomicInteger submitImage = new AtomicInteger(0);
    public static AtomicInteger getView = new AtomicInteger(0);

    private static AppConfig config;
    private Configuration configuration;
    private Context appContext;

    private AppConfig(Context context) {
        this.appContext = context;
    }

    public static AppConfig create(Context context) {
        if (config == null) {
            config = new AppConfig(context.getApplicationContext());
            initialize();
        }
        return config;
    }

    public Context getAppContext() {
        return this.appContext;
    }

    private static void initialize() {
        //TODO
    }

    public synchronized static AppConfig getInstance() {
        return config;
    }

    /**
     * set the debuggable
     *
     * @param debuggable
     */
    public void setDebuggable(boolean debuggable) {
        this.debuggable = debuggable;
    }

    /**
     * @return debuggable
     */
    public boolean isDebuggable() {
        return this.debuggable;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}