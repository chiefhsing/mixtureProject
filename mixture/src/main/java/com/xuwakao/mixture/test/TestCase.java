package com.xuwakao.mixture.test;

import android.os.Handler;
import android.os.Looper;

import com.xuwakao.mixture.framework.http.HttpAbsTaskWrapper;
import com.xuwakao.mixture.framework.http.HttpBaseTask;
import com.xuwakao.mixture.framework.http.HttpWorkPriority;
import com.xuwakao.mixture.framework.test.HttpTestTask;
import com.xuwakao.mixture.framework.test.TestImage;

/**
 * Created by duowan on 13-9-9.
 */
public class TestCase {

    /**
     * Test http module framwork
     * @param looper
     */
    public static void httpModuleTest(Looper looper){
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

            final HttpTestTask task = new HttpTestTask(param, looper);
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

    public static void imageFetchTest(Looper looper){
        for(int i = 0; i < 10; i++){
            String url = TestImage.imageUrls[i];
            HttpAbsTaskWrapper.HttpRequestParamBase param = new HttpAbsTaskWrapper.HttpRequestParamBase();
            param.url = url;
            HttpTestTask task = new HttpTestTask(param, looper);
            task.submit();
        }
    }
}
