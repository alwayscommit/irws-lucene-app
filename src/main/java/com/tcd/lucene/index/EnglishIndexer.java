package com.tcd.lucene.index;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class EnglishIndexer extends Indexer {

	public EnglishIndexer() throws IOException, URISyntaxException {
		super();
	}
	
	public void configureAnalyser() throws IOException, URISyntaxException {
		this.analyzer = new EnglishAnalyzer(IndexingUtils.getStopwords());
	}

}
