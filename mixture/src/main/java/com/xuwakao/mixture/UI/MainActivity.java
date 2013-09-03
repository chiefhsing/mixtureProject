package com.xuwakao.mixture.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.Utils.FileUtils;
import com.xuwakao.mixture.Utils.MLog;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = MLog.makeLogTag(MainActivity.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MLog.verbose(TAG, FileUtils.getInternalCacheDir(this).getPath());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
