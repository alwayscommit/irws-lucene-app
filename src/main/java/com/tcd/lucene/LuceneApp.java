package com.tcd.lucene;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;

import com.tcd.lucene.index.EnglishIndexer;
import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.model.FTDocument;
import com.tcd.lucene.model.LATimesDocument;
import com.tcd.lucene.model.LuceneDocumentConverter;
import com.tcd.lucene.parse.FBISParser;
import com.tcd.lucene.parse.FR94Parser;
import com.tcd.lucene.parse.FTParser;
import com.tcd.lucene.parse.LATimesParser;
import com.tcd.lucene.util.Utils;

public class LuceneApp {

	private static final String FBIS_PATH = "FBIS_PATH";
	private static final String FR94_PATH = "FR94_PATH";
	private static final String FT_PATH = "FT_PATH";
	private static final String LATIMES_PATH = "LATIMES_PATH";

	private static final String INDEX_DIRECTORY = "../index";

	// private static final String TEST_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\test\\";
	private static List<FBISDocument> fbisDocList = new ArrayList<FBISDocument>();
	private static List<FR94Document> fr94DocList = new ArrayList<FR94Document>();
	private static List<FTDocument> ftDocList = new ArrayList<FTDocument>();
	private static List<LATimesDocument> laTimesDocList = new ArrayList<LATimesDocument>();

	// list of documents that will be added to the index
	private static List<Document> luceneDocuments = new ArrayList<Document>();

	public static void main(String[] args) throws IOException {
		try {

			createOutputDirectory();

			Utils util = new Utils();
			Properties properties = util.getProperties(args[0]);

			System.out.println("Reading FBIS Documents :: " + properties.getProperty(FBIS_PATH));
			FBISParser.parse(properties.getProperty(FBIS_PATH), fbisDocList);
			LuceneDocumentConverter.convertFBIS(fbisDocList, luceneDocuments);
			indexDocs(luceneDocuments);
			fbisDocList.clear();
			luceneDocuments.clear();

			System.out.println("Reading FR94 Documents :: " + properties.getProperty(FR94_PATH));
			FR94Parser.parseNestedFolders(new File(properties.getProperty(FR94_PATH)).listFiles(), fr94DocList);
			LuceneDocumentConverter.convertFR94(fr94DocList, luceneDocuments);
			indexDocs(luceneDocuments);
			fr94DocList.clear();
			luceneDocuments.clear();

			System.out.println("Reading FT Documents :: " + properties.getProperty(FT_PATH));
			FTParser.parseNestedFolders(new File(properties.getProperty(FT_PATH)).listFiles(), ftDocList);
			LuceneDocumentConverter.convertFT(ftDocList, luceneDocuments);
			indexDocs(luceneDocuments);
			ftDocList.clear();
			luceneDocuments.clear();

			System.out.println("Reading LA Times Documents:: " + properties.getProperty(LATIMES_PATH));
			LATimesParser.parse(properties.getProperty(LATIMES_PATH), laTimesDocList);
			LuceneDocumentConverter.convertLATimes(laTimesDocList, luceneDocuments);
			indexDocs(luceneDocuments);
			laTimesDocList.clear();
			luceneDocuments.clear();

			System.out.println("done");

		} catch (IOException | IllegalAccessException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static void createOutputDirectory() throws IOException {
		File folder = new File(INDEX_DIRECTORY);
		if (folder.exists()) {
			FileUtils.deleteDirectory(folder);
		}
		folder.mkdir();
	}

	public static void indexDocs(List<Document> luceneDocuments) throws IOException, URISyntaxException {
		// StandardIndexer indexer = new StandardIndexer();
		EnglishIndexer indexer = new EnglishIndexer();
		System.out.println("Indexing using :: " + indexer.getClass().getSimpleName());
		indexer.processIndex(luceneDocuments, INDEX_DIRECTORY);
	}

}
