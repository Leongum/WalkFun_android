package com.G5432.HttpClient;

import com.G5432.Utils.UserUtil;
import com.loopj.android.http.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientHelper {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static Map<String, String> defaultHeaders = new HashMap<String, String>();

    public static Map<String, String> postDefaultHeaders = new HashMap<String, String>();

    public HttpClientHelper() {
        defaultHeaders.put("Content-Type", "application/json");
        defaultHeaders.put("Accept-Encoding", "gzip");

        postDefaultHeaders.put("Content-Type", "application/json");
        postDefaultHeaders.put("Content-Encoding", "gzip");

        String key = MessageFormat.format("{0}#{1}",UserUtil.getUserUuid(), UserUtil.getUserId());

        postDefaultHeaders.put("X-CLIENT-KEY", key);
        defaultHeaders.put("X-CLIENT-KEY", key);
    }

    public static HttpEntity convertHttpEntity(String entity) {
        try {
            return new StringEntity(entity, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Header[] convertHeaders(Map<String, String> customHttpHeader) {
        Header[] headers = null;
        if (customHttpHeader != null) {
            headers = new Header[customHttpHeader.size()];
            int index = 0;
            // put all HTTP headers
            for (Map.Entry<String, String> entity : customHttpHeader.entrySet()) {
                headers[index] = new BasicHeader(entity.getKey(), entity.getValue());
                index++;
            }
        }
        return headers;
    }

    public static void get(String url, Map<String, String> customHttpHeader, AsyncHttpResponseHandler responseHandler) {
        if (customHttpHeader == null) {
            customHttpHeader = new HashMap<String, String>();
        }
        customHttpHeader.putAll(defaultHeaders);
        client.get(null, url, convertHeaders(customHttpHeader), null, responseHandler);
    }

    public static void post(String url, Map<String, String> customHttpHeader, String entity, AsyncHttpResponseHandler responseHandler) {
        if (customHttpHeader == null) {
            customHttpHeader = new HashMap<String, String>();
        }
        customHttpHeader.putAll(postDefaultHeaders);
        client.post(null, url, convertHeaders(customHttpHeader), convertHttpEntity(entity), "application/json", responseHandler);
    }

    public static void put(String url, Map<String, String> customHttpHeader, String entity, AsyncHttpResponseHandler responseHandler) {
        if (customHttpHeader == null) {
            customHttpHeader = new HashMap<String, String>();
        }
        customHttpHeader.putAll(postDefaultHeaders);
        client.put(null, url, convertHeaders(customHttpHeader), convertHttpEntity(entity), "application/json", responseHandler);
    }
}
