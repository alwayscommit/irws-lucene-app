package com.tcd.lucene.index;

import com.tcd.lucene.analyzer.WordNetAnalyzer;

import java.io.IOException;
import java.net.URISyntaxException;

public class WordNetIndexer extends Indexer {

    public WordNetIndexer() throws IOException, URISyntaxException {
        super();
    }

    public void configureAnalyser() throws IOException, URISyntaxException {
        this.analyzer = new WordNetAnalyzer();
    }

}
