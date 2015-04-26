package cn.edu.bit.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class HelperProperties {

	private static Properties prop;
	static {

		prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/common.properties");
		try {
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String property) {

		try {
			return prop.getProperty(property).trim();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
}
