package com.xuwakao.mixture.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.Utils.FileUtils;
import com.xuwakao.mixture.Utils.MLog;
import com.xuwakao.mixture.httpmodule.HttpBaseTask;
import com.xuwakao.mixture.test.HttpTestTask;
import com.xuwakao.mixture.httpmodule.HttpWorkPriority;

public class MainActivity extends Activity {
    private static final String TAG = MLog.makeLogTag(MainActivity.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MLog.verbose(TAG, FileUtils.getInternalCacheDir(this).getPath());

        HttpBaseTask.HttpRequestParamBase param = new HttpBaseTask.HttpRequestParamBase();
        param.url = "http://xuwakao.com";
        param.priority = HttpWorkPriority.DEFAULT;
        param.retryCount = 10;
        final HttpTestTask task = new HttpTestTask(param, getMainLooper());
        task.submit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                task.cancel(false);
            }
        },5000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
