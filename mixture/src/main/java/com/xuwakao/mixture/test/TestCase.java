package com.xuwakao.mixture.test;

import android.os.Handler;
import android.os.Looper;

import com.xuwakao.mixture.framework.http.HttpTask;
import com.xuwakao.mixture.framework.http.HttpTaskRequestParam;
import com.xuwakao.mixture.framework.multiTask.TaskPriority;
import com.xuwakao.mixture.framework.test.TestImage;

/**
 * Created by xujiexing on 13-9-9.
 */
public class TestCase {

    /**
     * Test multiple task framwork
     * @param looper
     */
    public static void httpModuleTest(Looper looper){
        HttpTask task08 = null;
        HttpTask task03 = null;

        for(int i = 1000; i < 1020; i++){
            HttpTaskRequestParam param = new HttpTaskRequestParam();
            param.url = "http://xuwakao.com/index=" + i;
            param.priority = TaskPriority.DEFAULT;
            param.retryCount = 0;
            if(i == 1013 || i == 1007 || i == 1019){
                param.priority = TaskPriority.HIGH;
            }

            if(i == 1012 || i == 1009){
                param.priority = TaskPriority.LOW;
            }

            final HttpTask task = new HttpTask(param, looper);
            if(i == 1003){
                task03 = task;
            }

            if(i == 1008){
                task08 = task;
            }
            task.submit();
        }

        final HttpTask finalTask03 = task03;
        final HttpTask finalTask08 = task08;
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

    public static void imageFetchTest(Looper looper){
        for(int i = 0; i < 10; i++){
            String url = TestImage.imageUrls[i];
            HttpTaskRequestParam param = new HttpTaskRequestParam();
            param.url = url;
            HttpTask task = new HttpTask(param, looper);
            task.submit();
        }
    }
}
