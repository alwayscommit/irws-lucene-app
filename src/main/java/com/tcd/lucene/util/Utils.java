package com.tcd.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public Properties getProperties(String configPath) throws IOException {
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configPath);
		prop.load(inputStream);
		return prop;
	}

}
