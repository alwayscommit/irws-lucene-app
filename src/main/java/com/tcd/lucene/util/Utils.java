package com.tcd.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public static Properties getProperties(String configPath) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(configPath);
		properties.load(inputStream);
		return properties;
	}

}
