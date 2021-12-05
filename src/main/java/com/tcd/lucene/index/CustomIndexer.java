package com.tcd.lucene.index;

import java.io.IOException;
import java.net.URISyntaxException;

import com.tcd.lucene.analyzer.CustomAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class CustomIndexer extends Indexer {

	public CustomIndexer() throws IOException, URISyntaxException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configureAnalyser() throws IOException, URISyntaxException {
		this.analyzer = new CustomAnalyzer(IndexingUtils.getStopwords());
		
	}

}
