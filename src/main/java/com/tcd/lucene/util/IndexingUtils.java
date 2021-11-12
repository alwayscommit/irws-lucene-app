package com.tcd.lucene.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.CharArraySet;

public class IndexingUtils {

	/*private static List<String> customStopWords = Arrays.asList("how", "do", "when", "any", "has", "have", "from", "which", "why", "find",  "you", "can", "get",  "does", "what", "can't", "far", "anyone", "information", "references", "without", "however", "papers", "else", "progress", "investigations", "readily", "possible", "parameters", "available", "likely", "been", "work", "report", "solutions", "problem", "applicable", "done", "close");
	
	//Method to return a total list of stopwords
	public static CharArraySet getAllStopWords() {
		CharArraySet stopSet = CharArraySet.copy(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
		stopSet.addAll(customStopWords);
		return stopSet;
	}*/

	public static CharArraySet getStopwords() throws IOException, URISyntaxException {
		InputStream stopwordsStream = IndexingUtils.class.getClassLoader().getResourceAsStream("stopwords.txt");
		String text = new String(IOUtils.toByteArray(stopwordsStream));
		final List<String> stopWords = Arrays.asList(text.split(" "));
		final CharArraySet stopSet = new CharArraySet(stopWords, false);
		return CharArraySet.unmodifiableSet(stopSet);
	}
}
