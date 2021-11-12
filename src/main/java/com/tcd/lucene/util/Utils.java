package com.tcd.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public Properties getProperties(String configPath) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configPath);
		properties.load(inputStream);
		return properties;
	}

}
