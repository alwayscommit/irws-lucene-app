package com.tcd.lucene.indexer;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class EnglishIndexer extends Indexer {

	public EnglishIndexer() {
		super();
	}
	public void configureAnalyser() {
	  	System.out.println("Processing English Analyser");
		this.analyzer = new EnglishAnalyzer(IndexingUtils.getAllStopWords());
	}

}
