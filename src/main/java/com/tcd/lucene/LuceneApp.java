package com.tcd.lucene;

import java.io.File;
import java.util.List;

import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.parser.FBISParser;

//parse FR94
//parse FT
//parse latimes

public class LuceneApp {
	
	//jsoup -> java models -> search query (lucene query model) -> 
	//jsoup -> java models -> build the fields to index on StringField TextField (lucene)

	// parse FBIS
	private static final String FBIS_PATH = "D:\\AAATrinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\data\\fbis\\";

	public static void main(String[] args) {
		
		List<FBISDocument> fbisDocList = FBISParser.parse(FBIS_PATH);
		
		
		

	}

}
