package com.tcd.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.model.FTDocument;
import com.tcd.lucene.parser.FTParser;
import com.tcd.lucene.parser.LATimesParser;

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
	private static List<FTDocument> laTimesDocList = new ArrayList<FTDocument>();
	
	public static void main(String[] args) {
		try {
//			fbisDocList = FBISParser.parse(FBIS_PATH);
//			System.out.println(fbisDocList.size());
//			FR94Parser.parseNestedFolders(new File(FR94_PATH).listFiles(), fr94DocList);
//			System.out.println(fr94DocList.size());
			
//			FTParser.parseNestedFolders(new File(FT_PATH).listFiles(), ftDocList);
//			System.out.println(ftDocList.size());
			
			LATimesParser.parseNestedFolders(new File(LATIMES_PATH).listFiles(), laTimesDocList);
			System.out.println(laTimesDocList.size());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
