package com.tcd.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.CharArraySet;

public class IndexingUtils {

	public static CharArraySet getStopwords() throws IOException, URISyntaxException {
		InputStream stopwordsStream = IndexingUtils.class.getClassLoader().getResourceAsStream("stopwords2.txt");
		String text = new String(IOUtils.toByteArray(stopwordsStream));
		final List<String> stopWords = Arrays.asList(text.split(" "));
		final CharArraySet stopSet = new CharArraySet(stopWords, false);
		return CharArraySet.unmodifiableSet(stopSet);
	}
}
