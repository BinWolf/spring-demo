package com.wolf.commons.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	private static Map<String, Properties> PROPERTIES_MAP = new HashMap<String, Properties>();

	/**
	 * 获取配置文件内容
	 * @param fileName
	 * @param filed
	 * @return
	 */
	public static String getString(String fileName, String filed) {
		Properties propertiesConfig = getConfig(fileName);
		if(propertiesConfig == null) {
			return null;
		}
		return propertiesConfig.getProperty(filed);
	}


	/**
	 * 获取配置文件
	 * @param fileName
	 * @return
	 */
	public static Properties getConfig(String fileName) {
		Properties propertiesConfig = PROPERTIES_MAP.get(fileName);
		if(propertiesConfig != null) {
			return propertiesConfig;
		}
		propertiesConfig = PropertiesUtil.load(fileName);
		if(propertiesConfig != null) {
			PROPERTIES_MAP.put(fileName, propertiesConfig);
		}
		return propertiesConfig;
	}

	/**
	 * 获取配置文件
	 * @param fileName
	 * @return
	 */
	public static Properties load(String fileName) {

		if(fileName == null || fileName.trim().length() == 0){
			return null;
		}
		Properties p = new Properties();
		try {
			InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
			if (is != null) {
				p.load(is);
				is.close();
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return p;
	}

}
