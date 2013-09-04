package com.xuwakao.mixture.httpmodule;

/**
 * Created by xujiexing on 13-9-3.
 *
 * The core of http module.
 *
 * Responsiable for task schedule.
 */
public class HttpService {

    private static HttpService service;

    private HttpService(){}

    public static HttpService getInstance(){
        if(service == null){
            service = new HttpService();
        }

        return service;
    }
}
