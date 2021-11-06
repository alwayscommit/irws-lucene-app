package com.tcd.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class LuceneApp {

	private static final String FBIS_PATH = "D:\\- Trinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\Assignment Two\\fbis\\";
	private static final String FR94_PATH = "D:\\- Trinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\Assignment Two\\fr94\\";
	private static final String FT_PATH = "D:\\- Trinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\Assignment Two\\ft\\";
	private static final String LATIMES_PATH = "D:\\- Trinity\\Information Retrieval and Web Search\\Assignment\\Assignment 2\\Assignment Two\\latimes\\";

	public static void main(String[] args) {

		try {
			List<String> datasetPathList = Arrays.asList(FBIS_PATH, FR94_PATH, FT_PATH, LATIMES_PATH);
			for (String dataPath : datasetPathList) {
				File fbisDirectory = new File(dataPath);
				Set<String> tagList = new HashSet<String>();
				getTags(fbisDirectory.listFiles(), tagList);
				System.out.println(fbisDirectory.getName() + " :: " + tagList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void getTags(File[] fbisDirectory, Set<String> tagList) throws IOException {
		FileInputStream fis;
		for (File file : fbisDirectory) {
			if (file.isDirectory()) {
				getTags(file.listFiles(), tagList);
			} else {
				fis = new FileInputStream(file);
				Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
				for (Element rootElement : doc.select(Constants.FBIS.DOC)) {
					Elements elementList = rootElement.getAllElements();
					for (Element element : elementList) {
						tagList.add(element.tagName());
					}
				}
			}
		}
	}

}
