package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author feihang.chen Properties配置文件处理类
 */
public class PropertiesUtil {

	private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	private static final String[] files = { "export.properties"};
	private static Properties properties = new Properties();
	private static long refreshInterval = 1000 * 60 * 3;
	private static Map<String, Long> modMap = new HashMap<String, Long>();
	public static long lastTime = System.currentTimeMillis();

	static {
		load();
	}

	/**
	 * 
	 * <p>
	 * 方法名称: load|描述:循环加载配置文件
	 * </p>
	 */
	private static void load() {
		File configFile = null;
		InputStream in = null;
		for (String file : files) {
			URL url = PropertiesUtil.class.getClassLoader().getResource(file);
			configFile = new File(URLDecoder.decode(url.getPath()));
			// 最新修改时间
			Long lastModifiedTime = configFile.lastModified();
			// 最后一次修改时间
			Long oldModifiedTime = modMap.get(file);
			// 若未改动，则不再加载
			if (oldModifiedTime != null && lastModifiedTime.longValue() == oldModifiedTime.longValue())
				continue;
			// 更新更改时间
			modMap.put(file, lastModifiedTime);
			// 加载
			try {
				in = new FileInputStream(configFile);
				properties.load(in);
				in.close();
			} catch (IOException e) {
				log.error("加载配置文件异常", e);
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						log.error("加载配置文件时，关闭输入流异常", e);
					}
					in = null;
				}
			}
		}
	}

	/**
	 * <p>
	 * 方法名称: reflesh|描述:刷新配置文件
	 * </p>
	 */
	private static void reflesh() {
		long now = System.currentTimeMillis();
		if (now - lastTime > refreshInterval) {
			load();
			lastTime = now;
		}
	}

	/**
	 * <p>
	 * 方法名称: getMessage|描述:根据键获取键值
	 * </p>
	 * @param key
	 * @return
	 */
	public static String getMessage(String key) {
		reflesh();
		return null == properties.getProperty(key) ? "" : properties.getProperty(key).trim();
	}

}
