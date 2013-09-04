package com.xuwakao.mixture.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.Utils.FileUtils;
import com.xuwakao.mixture.Utils.MLog;
import com.xuwakao.mixture.httpmodule.HttpBaseTask;

public class MainActivity extends Activity {
    private static final String TAG = MLog.makeLogTag(MainActivity.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MLog.verbose(TAG, FileUtils.getInternalCacheDir(this).getPath());

        HttpBaseTask task = new HttpBaseTask(null);
        task.submit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
