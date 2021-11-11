package com.tcd.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.document.Document;

import com.tcd.lucene.indexer.EnglishIndexer;
import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.model.FTDocument;
import com.tcd.lucene.model.LATimesDocument;
import com.tcd.lucene.model.LuceneDocumentConverter;
import com.tcd.lucene.parser.FBISParser;
import com.tcd.lucene.parser.FR94Parser;
import com.tcd.lucene.parser.FTParser;
import com.tcd.lucene.parser.LATimesParser;
import com.tcd.lucene.util.Utils;

public class LuceneApp {

	private static final String FBIS_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\fbis\\";
	private static final String FR94_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\fr94\\";
	private static final String FT_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\ft\\";
	private static final String LATIMES_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\latimes\\";

	// private static final String TEST_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\test\\";
	private static List<FBISDocument> fbisDocList = new ArrayList<FBISDocument>();
	private static List<FR94Document> fr94DocList = new ArrayList<FR94Document>();
	private static List<FTDocument> ftDocList = new ArrayList<FTDocument>();
	private static List<LATimesDocument> laTimesDocList = new ArrayList<LATimesDocument>();

	// list of documents that will be added to the index
	private static List<Document> luceneDocuments = new ArrayList<Document>();

	public static void main(String[] args) throws IOException {
		try {
			
			Utils util = new Utils();
			Properties properties = util.getProperties(args[0]);
			
			System.out.println(properties.get("hello"));

			FBISParser.parse(FBIS_PATH, fbisDocList);
			LuceneDocumentConverter.indexFBIS(fbisDocList, luceneDocuments);

			FR94Parser.parseNestedFolders(new File(FR94_PATH).listFiles(), fr94DocList);
			LuceneDocumentConverter.indexFR94(fr94DocList, luceneDocuments);

			FTParser.parseNestedFolders(new File(FT_PATH).listFiles(), ftDocList);
			LuceneDocumentConverter.indexFT(ftDocList, luceneDocuments);

			LATimesParser.parse(LATIMES_PATH, laTimesDocList);
			LuceneDocumentConverter.indexLATimes(laTimesDocList, luceneDocuments);

			// StandardIndexer indexer = new StandardIndexer();
			EnglishIndexer indexer = new EnglishIndexer();
			indexer.processIndex(luceneDocuments);

			System.out.println("done");

		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
