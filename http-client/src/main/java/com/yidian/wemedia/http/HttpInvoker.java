package com.yidian.wemedia.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;

public abstract class HttpInvoker {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String DEFAULT_ENCODING = "UTF-8";

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

//    protected String baseUrl;

    protected static PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
    static {
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(10);
    }
    protected static HttpClient client = new DefaultHttpClient(connectionManager);
    static {
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1 * 1000);
        client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1 * 1000);
    }

    public HttpInvoker() {
//        this.baseUrl = baseUrl;
    }

    private void abortRequest(HttpRequestBase request) {
        try {
            request.abort();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected <T> T httpGet(String url, TypeReference<T> typeRef) {
        long startTime = System.currentTimeMillis();
        HttpGet get = new HttpGet(url);
        T ret = null;
        try {
            HttpResponse response = client.execute(get);
            log.debug("Response: " + response.toString());
            ret = OBJECT_MAPPER.readValue(response.getEntity().getContent(), typeRef);
            log.debug("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
            abortRequest(get);
        } finally {
        }
        log.debug("Http(s) call : GET " + url + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected <T> T httpPost(String url, String data, TypeReference<T> typeRef) {
        long startTime = System.currentTimeMillis();
        Header header = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
        HttpPost post = new HttpPost(url);
        post.setHeader(header);
        T ret = null;
        try {
            StringEntity postBody = new StringEntity(data, "UTF-8");
            // postBody.setContentEncoding(header);
            post.setEntity(postBody);
            HttpResponse response = client.execute(post);
            log.debug("Response: " + response.toString());
            ret = OBJECT_MAPPER.readValue(response.getEntity().getContent(), typeRef);
            log.debug("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
            abortRequest(post);
        } finally {
        }
        log.debug("Http(s) call : POST " + url + " DATA :" + data + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected JsonNode httpPost(String url, String data) {
        long startTime = System.currentTimeMillis();
        Header header = new BasicHeader(HTTP.CONTENT_TYPE, "application/com.bingo.json");
        HttpPost post = new HttpPost(url);
        post.setHeader(header);
        JsonNode ret = null;
        try {
            StringEntity postBody = new StringEntity(data, "UTF-8");
            // postBody.setContentEncoding(header);
            post.setEntity(postBody);
            HttpResponse response = client.execute(post);
            log.debug("Response: " + response.toString());
            ret = OBJECT_MAPPER.readTree(response.getEntity().getContent());
            log.info("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
        } finally {
            abortRequest(post);
        }
        log.info("Http(s) call : POST " + url + " DATA :" + data + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected JsonNode httpDelete(String url) {
        long startTime = System.currentTimeMillis();
        Header header = new BasicHeader(HTTP.CONTENT_TYPE, "application/com.bingo.json");
        HttpDelete delete = new HttpDelete(url);
        delete.setHeader(header);
        JsonNode ret = null;
        try {
            HttpResponse response = client.execute(delete);
            log.debug("Response: " + response.toString());
            ret = OBJECT_MAPPER.readTree(response.getEntity().getContent());
            log.info("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
        } finally {
            abortRequest(delete);
        }
        log.info("Http(s) call : DELETE " + url + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected JsonNode httpGet(String url) {
        long startTime = System.currentTimeMillis();
        HttpGet get = new HttpGet(url);
        JsonNode ret = null;
        try {
            HttpResponse response = client.execute(get);
            log.debug("Response: " + response.toString());
            ret = OBJECT_MAPPER.readTree(EntityUtils.toString(response.getEntity()));
            log.info("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
        } finally {
            abortRequest(get);
        }
        log.info("Http(s) call : GET " + url + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected String httpGetStringResult(String url) {
        long startTime = System.currentTimeMillis();
        HttpGet get = null;
        String ret = null;
        try {
            get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            log.debug("Response: " + response.toString());
            ret = EntityUtils.toString(response.getEntity());
            log.info("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
        } finally {
            abortRequest(get);
        }
        log.info("Http(s) call :" + url + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected String httpPostStringResult(String url, String data) {
        long startTime = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        String ret = null;
        try {
            if (data != null) {
                HttpEntity postBody = new StringEntity(data, "UTF-8");
                post.setEntity(postBody);
            }
            HttpResponse response = client.execute(post);
            log.debug("Response: " + response.toString());
            ret = EntityUtils.toString(response.getEntity());
            log.info("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (Exception e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
        } finally {
            abortRequest(post);
        }
        log.info("Http(s) call :" + url + " time: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected HttpResponse httpPostRawResponse(String url, String data) throws IOException {
        long startTime = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        HttpResponse ret = null;
        try {
            HttpEntity postBody = new StringEntity(data, "UTF-8");
            post.setEntity(postBody);
            ret = client.execute(post);
            log.debug("Response: " + ret.toString());
            log.debug("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (IOException e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
            throw e;
        } finally {
            abortRequest(post);
        }
        log.info("Http(s) call :" + url + " time:" + (System.currentTimeMillis() - startTime));
        return ret;
    }

    protected HttpResponse httpGetRawResponse(String url) throws IOException {
        long startTime = System.currentTimeMillis();
        HttpGet get = new HttpGet(url);
        HttpResponse ret = null;
        try {
            ret = client.execute(get);
            log.debug("Response: " + ret.toString());
            log.debug("Response Body:" + ret == null ? " error happens " : ret.toString());
        } catch (IOException e) {
            log.error("Http(s) call : " + url + " falied.");
            log.error("CAUSE " + e.getMessage(), e);
            throw e;
        } finally {
            abortRequest(get);
        }
        log.info("Http(s) call :" + url + " time:" + (System.currentTimeMillis() - startTime));
        return ret;
    }

    public static String urlEncoding(String in) {
        try {
            return URLEncoder.encode(in, "UTF-8");
        } catch (Exception e) {
            throw new IllegalArgumentException("failed to encode paremeter: " + in);
        }
    }

    public void destroy() throws Exception {
        try {
            client.getConnectionManager().shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

//    public String getBaseUrl() {
//        return baseUrl;
//    }
//
//    public void setBaseUrl(String baseUrl) {
//        this.baseUrl = baseUrl;
//    }
}

