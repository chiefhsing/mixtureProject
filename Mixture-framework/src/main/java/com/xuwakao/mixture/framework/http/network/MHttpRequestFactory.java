package com.xuwakao.mixture.framework.http.network;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.xuwakao.mixture.framework.utils.Utils;

import org.apache.http.HttpHost;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by xujiexing on 13-9-10.
 * <p/>
 * The factory used to build HttpRequest.
 * <p/>
 * The instance of this class is singleton so as to achieve maximum efficiency.See the docs of {@link #getInstance()}
 */
public class MHttpRequestFactory {
    private static MHttpRequestFactory transport;

    private Proxy netProxy;
    private SSLSocketFactory netSslSocketFactory;
    private HostnameVerifier netHostnameVerifier;

    private HttpHost apacheProxy;
    private org.apache.http.conn.ssl.SSLSocketFactory apacheSSLSocketFactory;
    private ProxySelector proxySelector;

    private HttpTransport httpTransport;
    private HttpRequestFactory requestFactory;

    private HttpRequestInitializer initializer;

    /**
     * For maximum efficiency, applications should use a single globally-shared instance of the HTTP transport.
     * <p/>
     * As a result, MHttpRequestFactory should be singleton.
     *
     * @return
     */
    public synchronized static MHttpRequestFactory getInstance() {
        if (transport == null) {
            transport = new MHttpRequestFactory();
        }
        return transport;
    }

    /**
     * Default constructor
     */
    private MHttpRequestFactory() {
        newCompatibleTransport();
    }

    /**
     * Get a Http Transport depends on the system version in consult of {@link com.google.api.client.extensions.android.http.AndroidHttp}
     * <p/>
     * -------------------Note-------------------
     * <p/>
     * For {@link com.google.api.client.extensions.android.http.AndroidHttp} is {@link com.google.api.client.util.Beta},not using it here.
     * <p/>
     * ------------------------------------------
     */
    private void newCompatibleTransport() {
        if (Utils.hasGingerbread()) {
            NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
            builder.setSslSocketFactory(netSslSocketFactory);
            builder.setProxy(netProxy);
            builder.setHostnameVerifier(netHostnameVerifier);
            this.httpTransport = builder.build();
        } else {
            ApacheHttpTransport.Builder builder = new ApacheHttpTransport.Builder();
            builder.setProxy(apacheProxy);
            builder.setSocketFactory(apacheSSLSocketFactory);
            builder.setProxySelector(proxySelector);
            this.httpTransport = builder.build();
        }
        this.requestFactory = this.httpTransport.createRequestFactory(initializer);
    }

    /**
     * Builds a request for the given HTTP method, URL, and content.
     *
     * @param requestMethod HTTP request method
     * @param url           HTTP request URL or {@code null} for none
     * @param content       HTTP request content or {@code null} for none
     * @return new HTTP request
     * @since 1.12
     */
    public synchronized HttpRequest buildRequest(String requestMethod, GenericUrl url, HttpContent content)
            throws IOException {
        return this.requestFactory.buildRequest(requestMethod, url, content);
    }

    /**
     * Builds a {@code DELETE} request for the given URL.
     *
     * @param url HTTP request URL or {@code null} for none
     * @return new HTTP request
     */
    public synchronized HttpRequest buildDeleteRequest(GenericUrl url) throws IOException {
        return buildRequest(HttpMethods.DELETE, url, null);
    }

    /**
     * Builds a {@code GET} request for the given URL.
     *
     * @param url HTTP request URL or {@code null} for none
     * @return new HTTP request
     */
    public synchronized HttpRequest buildGetRequest(GenericUrl url) throws IOException {
        return buildRequest(HttpMethods.GET, url, null);
    }

    /**
     * Builds a {@code POST} request for the given URL and content.
     *
     * @param url     HTTP request URL or {@code null} for none
     * @param content HTTP request content or {@code null} for none
     * @return new HTTP request
     */
    public synchronized HttpRequest buildPostRequest(GenericUrl url, HttpContent content) throws IOException {
        return buildRequest(HttpMethods.POST, url, content);
    }

    /**
     * Builds a {@code PUT} request for the given URL and content.
     *
     * @param url     HTTP request URL or {@code null} for none
     * @param content HTTP request content or {@code null} for none
     * @return new HTTP request
     */
    public synchronized HttpRequest buildPutRequest(GenericUrl url, HttpContent content) throws IOException {
        return buildRequest(HttpMethods.PUT, url, content);
    }

    /**
     * Builds a {@code PATCH} request for the given URL and content.
     *
     * @param url     HTTP request URL or {@code null} for none
     * @param content HTTP request content or {@code null} for none
     * @return new HTTP request
     */
    public synchronized HttpRequest buildPatchRequest(GenericUrl url, HttpContent content) throws IOException {
        return buildRequest(HttpMethods.PATCH, url, content);
    }

    /**
     * Builds a {@code HEAD} request for the given URL.
     *
     * @param url HTTP request URL or {@code null} for none
     * @return new HTTP request
     */
    public synchronized HttpRequest buildHeadRequest(GenericUrl url) throws IOException {
        return buildRequest(HttpMethods.HEAD, url, null);
    }

    public synchronized boolean supportsMethod(String method) throws IOException {
        return this.httpTransport.supportsMethod(method);
    }


    //--------------------------------------------------------------------------------
    //---------------------------------Getter/Setter----------------------------------
    //--------------------------------------------------------------------------------

    public Proxy getNetProxy() {
        return netProxy;
    }

    public void setNetProxy(Proxy netProxy) {
        this.netProxy = netProxy;
    }

    public SSLSocketFactory getNetSslSocketFactory() {
        return netSslSocketFactory;
    }

    public void setNetSslSocketFactory(SSLSocketFactory netSslSocketFactory) {
        this.netSslSocketFactory = netSslSocketFactory;
    }

    public HostnameVerifier getNetHostnameVerifier() {
        return netHostnameVerifier;
    }

    public void setNetHostnameVerifier(HostnameVerifier netHostnameVerifier) {
        this.netHostnameVerifier = netHostnameVerifier;
    }

    public HttpHost getApacheProxy() {
        return apacheProxy;
    }

    public void setApacheProxy(HttpHost apacheProxy) {
        this.apacheProxy = apacheProxy;
    }

    public org.apache.http.conn.ssl.SSLSocketFactory getApacheSSLSocketFactory() {
        return apacheSSLSocketFactory;
    }

    public void setApacheSSLSocketFactory(org.apache.http.conn.ssl.SSLSocketFactory apacheSSLSocketFactory) {
        this.apacheSSLSocketFactory = apacheSSLSocketFactory;
    }

    public HttpRequestInitializer getInitializer() {
        return initializer;
    }

    public void setInitializer(HttpRequestInitializer initializer) {
        this.initializer = initializer;
    }

    public ProxySelector getProxySelector() {
        return proxySelector;
    }

    public void setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }
}
