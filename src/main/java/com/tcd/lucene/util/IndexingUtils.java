package com.tcd.lucene.util;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

public class IndexingUtils {
	
	private static List<String> customStopWords = Arrays.asList("how", "do", "when", "any", "has", "have", "from", "which", "why", "find",  "you", "can", "get",  "does", "what", "can't", "far", "anyone", "information", "references", "without", "however", "papers", "else", "progress", "investigations", "readily", "possible", "parameters", "available", "likely", "been", "work", "report", "solutions", "problem", "applicable", "done", "close");
	
	//Method to return a total list of stopwords
	public static CharArraySet getAllStopWords() {
		CharArraySet stopSet = CharArraySet.copy(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
		stopSet.addAll(customStopWords);
		return stopSet;
	}
}
