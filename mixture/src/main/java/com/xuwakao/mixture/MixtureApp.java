package com.xuwakao.mixture;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;

import com.xuwakao.mixture.framework.AppConfig;
import com.xuwakao.mixture.utils.NetworkMonitor;

/**
 * Created by xuwakao on 13-8-28.
 */
public class MixtureApp extends Application{
    public static Context appContext;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    public void onCreate() {
        super.onCreate();

        doInit();
    }

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    public void onTerminate() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        AppConfig.getInstance().setConfiguration(newConfig);
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * do some initialized job
     */
    private void doInit(){
        appContext = this;
        AppConfig.create(getApplicationContext());

        initLog();
        initNetworkMonitor();
    }

    /**
     * Initialize the network state monitor
     */
    private void initNetworkMonitor() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(NetworkMonitor.getInstance(), filter);
    }

    /**
     * initialize all about log system
     */
    private void initLog(){
        AppConfig.getInstance().setDebuggable(isDebuggable());
    }

    /**
     * check whether or not app is debuggable depend on ApplicationInfo(AndroidManifest.xml debuggable value)
     * @return
     */
    private static boolean isDebuggable(){
        boolean debuggable = false;
        ApplicationInfo appInfo = null;
        PackageManager packMgmr = appContext.getPackageManager();
        try {
            appInfo = packMgmr.getApplicationInfo(appContext.getPackageName(),PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null) {
            /**
             * if debuggable == true in <application><application/> tag, the flags = 269008454,otherwise is falgs = 269008452
             *
             * the following expression can judge whether is debuggable or not
             *
             */
            debuggable = (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) > 0;
        }
        return debuggable;
    }
}
