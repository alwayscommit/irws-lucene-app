package com.tcd.lucene.index;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class EnglishIndexer extends Indexer {

	public EnglishIndexer() {
		super();
	}
	
	public void configureAnalyser() {
		this.analyzer = new EnglishAnalyzer(IndexingUtils.getAllStopWords());
	}

}
