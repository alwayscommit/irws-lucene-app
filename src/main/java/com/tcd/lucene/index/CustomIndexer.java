package com.tcd.lucene.index;

import java.io.IOException;
import java.net.URISyntaxException;

import com.tcd.lucene.analyzers.SynonymsAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class CustomIndexer extends Indexer {

    public CustomIndexer() throws IOException, URISyntaxException {
        super();
    }

    public void configureAnalyser() throws IOException, URISyntaxException {
        this.analyzer = new SynonymsAnalyzer(IndexingUtils.getStopwords());
    }

}
