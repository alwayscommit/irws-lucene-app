package com.tcd.lucene.index;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public abstract class Indexer {
	
	protected Analyzer analyzer;
	
	public Indexer() throws IOException, URISyntaxException {
		this.configureAnalyser();
	}

	// Method to processIndex
    public void processIndex( List<Document> documents, String indexDirectory) throws IOException {
    	System.out.println("Indexing with :: " + analyzer.getClass().getSimpleName());
		// Open the directory that contains the search index
		Directory directory = FSDirectory.open(Paths.get(indexDirectory));

		// Set up an index writer to add process and save documents to the index
		IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		IndexWriter iwriter = new IndexWriter(directory, config);

		// Write all the documents in the linked list to the search index
		iwriter.addDocuments(documents);

		// Commit everything and close
		iwriter.close();
		directory.close();
    }
    
    // Method to be overwritten to configure analyzers
    public abstract void configureAnalyser() throws IOException, URISyntaxException;
    
    public Analyzer getAnalyzer() {
    	return analyzer;
    }
}
