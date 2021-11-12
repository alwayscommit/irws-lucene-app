package com.tcd.lucene.index;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class StandardIndexer extends Indexer {

	public StandardIndexer() throws IOException, URISyntaxException {
		super();
	}

	public void configureAnalyser() throws IOException, URISyntaxException {
		this.analyzer = new StandardAnalyzer(IndexingUtils.getStopwords());
	}

}
