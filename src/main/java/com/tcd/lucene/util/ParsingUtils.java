package com.tcd.lucene.util;

import java.io.File;

public class ParsingUtils {
	
	public static boolean ignoreFile(File file) {
		if (file.getName().startsWith("~")) {
			return true;
		} else {
			return false;
		}
	}

}
