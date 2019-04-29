package utils.tts;

import com.google.gson.Gson;
import common.RetCode;
import exceptions.BusinessException;
import play.Logger;
import utils.MD5;
import utils.SysLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 
 * @author wangmh
 *
 */
public class MSCUtils {

	private static final String ROOT_DIR = "./tts";
	private final static String TTS_URI = "./tts/tts_";
	
	private List<File> mFiles = null;
	private Long mHospitalId = null;
	private static Gson gson = new Gson();
	
	//讯飞配置
	private static final String URL = "http://api.xfyun.cn/v1/service/v1/tts";
	private static final String APPID = "5b99cb4a";
	private static final String APIKEY = "1870ccf99f61138d5c3bf8740cc97a46";
	
	//test
//	private static final String APPID = "5b0b9faa";
//	private static final String APIKEY = "f7dda2d592c5604e68e7f70542188189";

	/**
	 * 将多个pcm文件合并
	 * 
	 * @throws FileNotFoundException
	 */
	private void mergeFile() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(TTS_URI + mHospitalId + ".wav");
			for (File file : mFiles) {
				fis = new FileInputStream(file);
				int len = -1;
				byte[] buffer = new byte[1024];
				while ((len = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.flush();
			}
			Logger.info("merge pcm file successful");
		} catch (IOException e) {
			SysLogger.error(e, e.getMessage());
		} finally {
			try {
				fis.close();
				fos.close();
				delOriginFile();
				ConverUtils.convertAudioFiles(TTS_URI + mHospitalId + ".wav", TTS_URI + mHospitalId + ".mp3");
			} catch (Exception e) {
				SysLogger.error(e, e.getMessage());
			}
		}
	}

	/**
	 * 删除源PCM文件
	 * 
	 * @throws IOException
	 */
	private void delOriginFile() throws IOException {
		for (File file : mFiles) {
			if (!file.exists()) {
				throw new IOException("file not exist!");
			} else {
				file.delete();
			}
		}
	}
	
	public void createTTS(List<MSCParams> paramsList, Long hospitalId) {
		mHospitalId = hospitalId;
		mFiles = new ArrayList<>();
		int countor = 1;
		
		File dirFile = new File(ROOT_DIR);
		if (!dirFile.exists() || dirFile.isFile()) {
			dirFile.mkdirs();
		}
		
		try {
			for (MSCParams mscParams : paramsList) {
				HttpURLConnection conn = postIfly(mscParams);
				String contentType = conn.getHeaderField("Content-type");
				InputStream is = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				if (contentType.equals("text/plain")) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					while ((len = is.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					Map result = gson.fromJson(baos.toString(), Map.class);
					SysLogger.error("call ifly error,code:%s,msg:%s", result.get("code"),result.get("desc"));
				}else if (contentType.equals("audio/mpeg")) {
					File file = new File(TTS_URI + mHospitalId + "_" + countor + ".wav");
					FileOutputStream fos = new FileOutputStream(file);
					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					fos.flush();
					fos.close();
					mFiles.add(file);
					countor++;
					if (paramsList.size() == countor - 1) {
						mergeFile();
					}
				}
			}
		}catch (Exception e) {
			SysLogger.error(e, e.getMessage());
			throw new BusinessException(RetCode.SERVER_ERROR);
		}
	}
	
	private HttpURLConnection postIfly(MSCParams mscParams) {
		try {
			URL url = new URL(URL);
			Long currTime = null;
			Map<String, String> params = new HashMap<>();
			currTime = System.currentTimeMillis() / 1000;
			params = new HashMap<>();
			params.put("auf", "audio/L16;rate=16000");
			params.put("aue", "raw");
			params.put("voice_name", mscParams.getSpeaker());
			params.put("speed", mscParams.getSpeed());
			params.put("volume", mscParams.getVolume());
			params.put("engine_type", "intp65");
			params.put("text_type", "text");

			String paramStr = gson.toJson(params);
			String paramsBase64 = Base64.getEncoder().encodeToString(paramStr.getBytes());

			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 讯飞参数
			conn.setRequestProperty("X-Appid", APPID);
			conn.setRequestProperty("X-CurTime", String.valueOf(currTime));
			conn.setRequestProperty("X-Param", paramsBase64);
			conn.setRequestProperty("X-CheckSum", MD5.getMD5(APIKEY + currTime + paramsBase64).toLowerCase());
			
			// 通用参数
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"), true);
			// 发送请求参数
			out.print("text=" + mscParams.getContent());
			out.flush();
			return conn;
		}catch (Exception e) {
			SysLogger.error(e, e.getMessage());
			return null;
		}
	}
	
}
