package com.tcd.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tcd.lucene.indexer.EnglishIndexer;
import com.tcd.lucene.model.*;
import com.tcd.lucene.parser.FBISParser;
import com.tcd.lucene.parser.FR94Parser;
import com.tcd.lucene.parser.FTParser;
import com.tcd.lucene.parser.LATimesParser;
import org.apache.lucene.document.Document;

//parse FR94
//parse FT
//parse latimes

public class LuceneApp {

	// jsoup -> java models -> search query (lucene query model) ->
	// jsoup -> java models -> build the fields to index on StringField TextField (lucene)

	// parse FBIS
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
			FBISParser.parse(FBIS_PATH, fbisDocList);
			System.out.println("FBIS Count :: " + fbisDocList.size());
			LuceneIndexModel.indexFBIS(fbisDocList, luceneDocuments);
			FR94Parser.parseNestedFolders(new File(FR94_PATH).listFiles(), fr94DocList);
			System.out.println("FR94 Count :: " + fr94DocList.size());
			LuceneIndexModel.indexFR94(fr94DocList, luceneDocuments);
			FTParser.parseNestedFolders(new File(FT_PATH).listFiles(), ftDocList);
			System.out.println("FT Count :: " + ftDocList.size());
			LuceneIndexModel.indexFT(ftDocList, luceneDocuments);
			LATimesParser.parse(LATIMES_PATH, laTimesDocList);
			System.out.println("LATimes Count8 :: " + laTimesDocList.size());
			LuceneIndexModel.indexLATimes(laTimesDocList, luceneDocuments);
		} catch (IOException e) {
			e.printStackTrace();
		}

// 		StandardIndexer indexer = new StandardIndexer();
        EnglishIndexer indexer = new EnglishIndexer();
        indexer.processIndex(luceneDocuments);
	}

}
