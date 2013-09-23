package com.xuwakao.mixture.framework.http;

import android.os.Looper;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.xuwakao.mixture.framework.AppConfig;
import com.xuwakao.mixture.framework.http.network.MHttpRequestFactory;
import com.xuwakao.mixture.framework.multiTask.AbsBaseAsyncTask;
import com.xuwakao.mixture.framework.multiTask.TaskAbsRequestParam;
import com.xuwakao.mixture.framework.multiTask.TaskAbsResult;
import com.xuwakao.mixture.framework.utils.FileUtils;
import com.xuwakao.mixture.framework.utils.MLog;
import com.xuwakao.mixture.framework.utils.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by xujiexing on 13-9-23.
 */
public class HttpTask extends AbsBaseAsyncTask {
    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    public HttpTask(TaskRequestParamBase param, Looper looper) {
        super(param, looper);
    }

    @Override
    protected TaskAbsResult executeJob(TaskAbsRequestParam param) throws Exception {
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "executeJob" + this + " before with mParams = " + param + " in thread = " + Thread.currentThread());
        FileResult result = new FileResult();
        HttpTaskRequestParam mParams = (HttpTaskRequestParam) param;

        result.url = mParams.url;
        HttpResponse response = MHttpRequestFactory.getInstance().buildGetRequest(new GenericUrl(mParams.url)).execute();
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "response = " + response.getMediaType() + ", " + response.getContentType());
        if (response.isSuccessStatusCode() && response.getContentType() != null) {
            String dir = FileUtils.getExternalCacheDir(AppConfig.getInstance().getAppContext()).getPath();
            result.filePath = dir + File.separator + UUID.randomUUID() + ".jpg";
            File file = new File(result.filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            response.download(outputStream);
            result.exception = null;
            outputStream.flush();
            outputStream.close();
        } else {
            //TODO
        }
        return result;
    }

    @Override
    protected void doneWithResult(TaskAbsResult result) {
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "doneWithResult " + this + " return reuslt = " + result);
    }

    private class FileResult extends HttpTaskResult {
        public String filePath;

        @Override
        public String toString() {
            return Utils.makeToString(FileResult.class, new Object[]{resultCode, url, exception, filePath});
        }
    }
}
