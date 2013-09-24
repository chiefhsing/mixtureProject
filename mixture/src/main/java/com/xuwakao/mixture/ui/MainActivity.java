package com.xuwakao.mixture.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.framework.utils.FileUtils;
import com.xuwakao.mixture.framework.utils.MLog;
import com.xuwakao.mixture.test.TestCase;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MLog.makeLogTag(MainActivity.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MLog.verbose(TAG, FileUtils.getInternalCacheDir(this).getPath());

//        TestCase.httpModuleTest(getMainLooper());
//        TestCase.imageFetchTest(getMainLooper());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
