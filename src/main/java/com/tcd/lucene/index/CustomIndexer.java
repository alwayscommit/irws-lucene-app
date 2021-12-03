package com.tcd.lucene.index;

import com.tcd.lucene.analyzers.SynonymsAnalyzer;

import java.io.IOException;
import java.net.URISyntaxException;

public class CustomIndexer extends Indexer {

    public CustomIndexer() throws IOException, URISyntaxException {
        super();
    }

    public void configureAnalyser() throws IOException, URISyntaxException {
        this.analyzer = new SynonymsAnalyzer();
    }

}