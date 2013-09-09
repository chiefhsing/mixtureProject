package com.xuwakao.mixture.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.framework.httpmodule.HttpBaseTask;
import com.xuwakao.mixture.framework.httpmodule.HttpWorkPriority;
import com.xuwakao.mixture.framework.test.HttpTestTask;
import com.xuwakao.mixture.framework.utils.MLog;
import com.xuwakao.mixture.utils.FileUtils;

public class MainActivity extends Activity {
    private static final String TAG = MLog.makeLogTag(MainActivity.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MLog.verbose(TAG, FileUtils.getInternalCacheDir(this).getPath());

        HttpTestTask task08 = null;
        HttpTestTask task03 = null;

        for(int i = 1000; i < 1020; i++){
            HttpBaseTask.HttpRequestParamBase param = new HttpBaseTask.HttpRequestParamBase();
            param.url = "http://xuwakao.com/index=" + i;
            param.priority = HttpWorkPriority.DEFAULT;
            param.retryCount = 10;
            if(i == 1013 || i == 1007 || i == 1019){
                param.priority = HttpWorkPriority.HIGH;
            }

            if(i == 1012 || i == 1009){
                param.priority = HttpWorkPriority.LOW;
            }

            final HttpTestTask task = new HttpTestTask(param, getMainLooper());
            if(i == 1003){
                task03 = task;
            }

            if(i == 1008){
                task08 = task;
            }
            task.submit();
        }

        final HttpTestTask finalTask03 = task03;
        final HttpTestTask finalTask08 = task08;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                assert finalTask03 != null;
                finalTask03.cancel(true);

                assert finalTask08 != null;
                finalTask08.cancel(true);
            }
        },5000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
