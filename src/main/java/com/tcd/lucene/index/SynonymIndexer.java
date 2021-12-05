package com.tcd.lucene.index;

import com.tcd.lucene.analyzer.SynonymsAnalyzer;

import java.io.IOException;
import java.net.URISyntaxException;

public class SynonymIndexer extends Indexer {

    public SynonymIndexer() throws IOException, URISyntaxException {
        super();
    }

    public void configureAnalyser() throws IOException, URISyntaxException {
        this.analyzer = new SynonymsAnalyzer();
    }

}
