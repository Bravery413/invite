package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import play.Logger;

import java.util.Map;

public class HttpUtil {

	public static final int CONNECT_TIMEOUT = 10 * 1000;

	private static CloseableHttpClient httpclient;

	static {
		RequestConfig config = RequestConfig.custom()
    		.setConnectionRequestTimeout(CONNECT_TIMEOUT)
    		.setConnectTimeout(CONNECT_TIMEOUT)
			.setSocketTimeout(CONNECT_TIMEOUT)
    		.setSocketTimeout(CONNECT_TIMEOUT).build();

		httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
	public static String get(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpGet);
		} catch (Exception e) {
			Logger.error(e, e.getMessage());
			return null;
		}
        
        HttpEntity entity = null;
        try {
        	if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        		return null;
        	}
        	entity = response.getEntity();
        	if (entity != null) {
        		String resp = EntityUtils.toString(entity, "UTF8");
        		return resp;
        	}
        }  catch (Exception e) {
        } finally {
        	if (entity != null) {
        		try {
        			EntityUtils.consume(entity);
        		} catch (Exception e) {
        		}
        	}
        	try {
        		response.close();
        	} catch (Exception e) {
        	}
        }
        return null;
	}

    public static String post(String url, String body) {
	    return postExt(url, body, null);
    }

	public static String postExt(String url, String body, Map<String, String> headers) {
		HttpPost httpPost = new HttpPost(url);
        
        StringEntity reqEntity = new StringEntity(body, "utf-8");
        httpPost.setEntity(reqEntity);

        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpPost);
		} catch (Exception e) {
			Logger.error(e, e.getMessage());
			return null;
		}
        
        HttpEntity entity = null;
        try {
        	if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        		return null;
        	}
        	entity = response.getEntity();
        	if (entity != null) {
        		String resp = EntityUtils.toString(entity, "UTF8");
        		return resp;
        	}
        }  catch (Exception e) {
        } finally {
        	if (entity != null) {
        		try {
        			EntityUtils.consume(entity);
        		} catch (Exception e) {
        		}
        	}
        	try {
        		response.close();
        	} catch (Exception e) {
        	}
        }
        return null;
	}
	
	public static <T> T get(String url, Class<T> respClazz) {
		String respStr = get(url);
		return JsonUtil.toBean(respStr, respClazz);
	}

	public static <T> T post(String url, Object reqObj, Class<T> respClazz) {
		String reqStr = "";
		if (reqObj != null) {
			reqStr = JsonUtil.toJson(reqObj);
		}
		String respStr = post(url, reqStr);
		return JsonUtil.toBean(respStr, respClazz);
	}
}
