package utils;

import com.google.gson.Gson;
import common.RetCode;
import exceptions.BusinessException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;
import play.Play;

import java.io.File;
import java.util.Map;
import java.util.Random;

public class LocalImageUtils {

	private static final String IMAGEURL;
	static {
		IMAGEURL = Play.configuration.getProperty("filesrv.addr", "");
	}

	/*public static void getImage(String imgPath, OutputStream os) throws Exception {
		try {
			String urlStr = IMAGEURL + "/getImage?path=" + imgPath;
			SysLogger.info("img url:[%s]", urlStr);
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				
				IOUtils.copy(is, os);
			}else {
				SysLogger.info("图片获取失败，返回码[%d]",responseCode);
			}
		} catch (Exception e) {
			SysLogger.error(e, e.getMessage());
			throw new BusinessException(RetCode.SERVER_ERROR);
		}
	}*/

	public static String uploadImg(File file) throws Exception {
		HttpClient httpClient = new HttpClient();
		String CALLER = Play.configuration.getProperty("filesrv.caller", "");
        String KEY = Play.configuration.getProperty("filesrv.key", "");
        
        long now = System.currentTimeMillis();
        String randstr = String.valueOf(new Random().nextInt(1000000));
        String sign = StringUtils.md5(KEY + CALLER + now + randstr);

        String verifyStr = String.format("?caller=%s&timestamp=%d&randstr=%s&sign=%s", CALLER, now, randstr, sign);
		try {
			StringBuffer url = new StringBuffer(IMAGEURL + "/uploadImage");
			url.append(verifyStr);
			PostMethod post = new PostMethod(url.toString());

			FileRequestEntity reqEntity = new FileRequestEntity(file, "application/x-ico");
			post.setRequestEntity(reqEntity);

			httpClient.executeMethod(post);
			int statusCode = post.getStatusCode();
			
			if (statusCode == HttpStatus.SC_OK) {

				String result = post.getResponseBodyAsString();

				Map<String, Object> resultMap = new Gson().fromJson(result, Map.class);
				String respurl = resultMap.get("data").toString();
				return respurl;
			}
			return "";
		} catch (Exception e) {
			SysLogger.error(e, e.getMessage());
			throw new BusinessException(RetCode.SERVER_ERROR);
		}
	}

}