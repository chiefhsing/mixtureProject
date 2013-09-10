package com.xuwakao.mixture.framework.test;

import android.os.Looper;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.xuwakao.mixture.framework.AppConfig;
import com.xuwakao.mixture.framework.http.HttpAbsRequestParam;
import com.xuwakao.mixture.framework.http.HttpAbsResult;
import com.xuwakao.mixture.framework.http.HttpBaseTask;
import com.xuwakao.mixture.framework.http.HttpServiceConfig;
import com.xuwakao.mixture.framework.http.network.MHttpRequestFactory;
import com.xuwakao.mixture.framework.utils.FileUtils;
import com.xuwakao.mixture.framework.utils.MLog;
import com.xuwakao.mixture.framework.utils.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;


/**
 * Created by xujiexing on 13-9-5.
 */
public class HttpTestTask extends HttpBaseTask {
    /**
     * Constructor
     *
     * @param param  The param used to execute the request
     * @param looper The looper of the thread which execute this task
     */
    public HttpTestTask(HttpAbsRequestParam param, Looper looper) {
        super(param, looper);
    }

    @Override
    protected HttpAbsResult executeJob(final HttpAbsRequestParam mParams) throws Exception {
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "executeJob" + this + " before with mParams = " + mParams + " in thread = " + Thread.currentThread());
        FileResult result = new FileResult();

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
    protected void doneWithResult(HttpAbsResult result) {
        MLog.verbose(HttpServiceConfig.HTTP_TASK_TAG, "doneWithResult " + this + " return reuslt = " + result);
    }

    private class FileResult extends HttpResultBase {
        public String filePath;

        @Override
        public String toString() {
            return Utils.makeToString(FileResult.class, new Object[]{resultCode, url, exception, filePath});
        }
    }
}
