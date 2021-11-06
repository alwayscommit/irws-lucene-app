package com.tcd.lucene;

import java.io.IOException;
import java.util.List;

import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.parser.FBISParser;
import com.tcd.lucene.parser.FR94Parser;

//parse FR94
//parse FT
//parse latimes

public class LuceneApp {

	// jsoup -> java models -> search query (lucene query model) ->
	// jsoup -> java models -> build the fields to index on StringField TextField (lucene)

	// parse FBIS
	private static final String FBIS_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\fbis\\";
	private static final String FR94_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\fr94\\";
	// private static final String TEST_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\test\\";
	private static List<FBISDocument> fbisDocList = null;
	private static List<FR94Document> fr94DocList = null;

	public static void main(String[] args) {
		try {
			fbisDocList = FBISParser.parse(FBIS_PATH);
			System.out.println(fbisDocList.size());
			fr94DocList = FR94Parser.parse(FR94_PATH);
			System.out.println(fr94DocList.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
