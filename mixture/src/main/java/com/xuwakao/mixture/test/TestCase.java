package com.xuwakao.mixture.test;

import android.os.Handler;
import android.os.Looper;

import com.xuwakao.mixture.framework.http.HttpTask;
import com.xuwakao.mixture.framework.http.HttpTaskRequestParam;
import com.xuwakao.mixture.framework.multiTask.AbsAysncFutureTaskWrapper;
import com.xuwakao.mixture.framework.multiTask.TaskAbsRequestParam;
import com.xuwakao.mixture.framework.multiTask.TaskPriority;
import com.xuwakao.mixture.framework.test.TestImage;

/**
 * Created by xujiexing on 13-9-9.
 */
public class TestCase {

    /**
     * Test multiple task framwork
     *
     * @param looper
     */
    public static void multipleTaskTest(Looper looper) {
        TestMultipleTask task08 = null;
        TestMultipleTask task03 = null;

        for (int i = 1000; i < 1020; i++) {
            AbsAysncFutureTaskWrapper.TaskRequestParamBase param = new AbsAysncFutureTaskWrapper.TaskRequestParamBase();
            param.priority = TaskPriority.DEFAULT;
            param.retryCount = 1;
            param.timeout = 1;
            if (i == 1013 || i == 1007 || i == 1019) {
                param.priority = TaskPriority.HIGH;
            }

            if (i == 1012 || i == 1009) {
                param.priority = TaskPriority.LOW;
            }

            final TestMultipleTask task = new TestMultipleTask(param, looper);
            if (i == 1003) {
                task03 = task;
            }

            if (i == 1008) {
                task08 = task;
            }
            task.submit();
        }

        final TestMultipleTask finalTask03 = task03;
        final TestMultipleTask finalTask08 = task08;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                assert finalTask03 != null;
                finalTask03.cancel(true);

                assert finalTask08 != null;
                finalTask08.cancel(true);
            }
        }, 5000);
    }

    public static void imageFetchTest(Looper looper) {
        for (int i = 0; i < 10; i++) {
            String url = TestImage.imageUrls[i];
            HttpTaskRequestParam param = new HttpTaskRequestParam();
            param.url = url;
            HttpTask task = new HttpTask(param, looper);
            task.submit();
        }
    }
}
