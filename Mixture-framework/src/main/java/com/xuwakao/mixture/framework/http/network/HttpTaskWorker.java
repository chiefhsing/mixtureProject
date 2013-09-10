package com.xuwakao.mixture.framework.http.network;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;

import java.io.IOException;

/**
 * Created by xujiexing on 13-9-9.
 *
 * Do the actual work about network connection.
 *
 */
public class HttpTaskWorker{

    /**
     * For maximum efficiency,
     * applications should use a single globally-shared instance of the HTTP transport.
     */
    private HttpTransport httpTransport;
    private HttpRequestFactory requestFactory;

    private static HttpTaskWorker worker;

    public static synchronized HttpTaskWorker getInstance(){
        if(worker == null){
            worker = new HttpTaskWorker();
        }
        return worker;
    }

    private HttpTaskWorker(){
        doInit();
    }

    private void doInit() {
//        this.httpTransport = AndroidHttp.newCompatibleTransport();
//        this.requestFactory = this.httpTransport.createRequestFactory();
    }

    public HttpRequest getGetRequest(String url) throws IOException {
        return MHttpRequestFactory.getInstance().buildGetRequest(new GenericUrl(url));
    }
}
