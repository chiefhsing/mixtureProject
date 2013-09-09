package com.xuwakao.mixture.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by xuwakao on 13-9-2.
 *
 * Network state monitor
 */
public class NetworkMonitor extends BroadcastReceiver{
//    private static final String TAG = MLog.makeLogTag(NetworkMonitor.class);

    private static final int NETWORK_UNAVAILABLE = -1;

    private static NetworkMonitor networkMonitor;
    private List<NetworkStateChanged> listeners = new LinkedList<NetworkStateChanged>();

    private NetworkMonitor(){
        super();
    }

    /**
     * Singleton
     *
     * @return
     */
    public static NetworkMonitor getInstance(){
        if(networkMonitor == null){
            networkMonitor = new NetworkMonitor();
        }
        return networkMonitor;
    }

    /**
     * Add listener
     *
     * @param networkStateChanged
     */
    public void addListener(NetworkStateChanged networkStateChanged){
        this.listeners.add(networkStateChanged);
    }

    /**
     * remove listener
     *
     * @param networkStateChanged
     */
    public void removeListener(NetworkStateChanged networkStateChanged){
        for(NetworkStateChanged n : this.listeners){
            if(n != null && n.equals(this.listeners)){
                this.listeners.remove(n);
                break;
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        MLog.verbose(TAG, "NetworkMonitor onReceive, intent = " + intent.getAction());
        if(this.listeners.isEmpty()){
            return;
        }

        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if(info == null){
                for(NetworkStateChanged nc : listeners){
                    nc.onDisconnected(NETWORK_UNAVAILABLE);
                }
                return;
            }

            if(info.isConnected()){
                for(NetworkStateChanged nc : listeners){
                    nc.onConnected(info.getType());
                }
            }else{
                if(info.isConnectedOrConnecting()){
                    for(NetworkStateChanged nc : listeners){
                        nc.onConnneting(info.getType());
                    }
                }else{
                    for(NetworkStateChanged nc : listeners){
                        nc.onDisconnected(info.getType());
                    }
                }
            }
        }
    }

    public interface NetworkStateChanged{
        void onConnneting(int type);
        void onConnected(int type);
        void onDisconnected(int type);
    }
}