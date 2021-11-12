package com.tcd.lucene.index;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import com.tcd.lucene.util.IndexingUtils;

public class StandardIndexer extends Indexer {
	
	public StandardIndexer() {
		super();
	}
	public void configureAnalyser() {
	  	System.out.println("Processing Standard Analyser");
	  	IndexingUtils utils = new IndexingUtils();
		this.analyzer = new StandardAnalyzer(utils.getAllStopWords());
	}
	

}
