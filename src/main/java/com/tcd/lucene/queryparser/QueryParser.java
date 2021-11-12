package com.tcd.lucene.queryparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.QueryModel;
import com.tcd.lucene.util.Constants;
import com.tcd.lucene.util.ParsingUtils;

public class QueryParser {

	public static void parseNestedFolders(File[] files, List<QueryModel> queries) throws IOException {
		for (File file : files) {
			if (ParsingUtils.ignoreFile(file)) {
				continue;
			}
			if (file.isDirectory()) {
				parseNestedFolders(file.listFiles(), queries);
			} else {
				FileInputStream fis = new FileInputStream(file);
				Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
				for (Element rootElement : doc.select(Constants.Query.TOP)) {
					QueryModel query = parseDoc(rootElement);
					queries.add(query);
				}
				fis.close();
			}
		}
	}

	private static QueryModel parseDoc(Element rootElement) {
		List<Element> elementList = rootElement.getAllElements();
		QueryModel query = new QueryModel();
		for (Element element : elementList) {
			buildModel(element, query);
		}
		return query;
	}

	private static void buildModel(Element element, QueryModel query) {
		switch (element.tagName()) {
		case Constants.Query.NUM:
			query.setQueryNumber(element.ownText());
			break;
		case Constants.Query.TITLE:
			query.setQueryTitle(element.ownText());
			break;
		case Constants.Query.DESC:
			query.setDescription(element.text());
			break;
		case Constants.Query.NARR:
			query.setNarrative(element.ownText());
			break;
		}
	}
}
