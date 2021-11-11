package com.tcd.lucene.indexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public abstract class Indexer {
	private static String INDEX_DIRECTORY = "../index";
	protected Analyzer analyzer;
	
	public Indexer() {
		this.configureAnalyser();
	}

	// Method to processIndex
    public void processIndex( List<Document> documents) throws IOException {

		// Open the directory that contains the search index
		Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));

		// Set up an index writer to add process and save documents to the index
		IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter iwriter = new IndexWriter(directory, config);
			

		// Write all the documents in the linked list to the search index
		iwriter.addDocuments(documents);

		// Commit everything and close
		iwriter.close();
		directory.close();
    }
    
    // Method to be overwritten to configure analyzers
    public abstract void configureAnalyser();
}
