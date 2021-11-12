package com.tcd.lucene.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.DocumentQuery;
import com.tcd.lucene.util.Constants;
import com.tcd.lucene.util.ParsingUtils;

public class QueryParser {

	public static List<DocumentQuery> parse(String file, List<DocumentQuery> queries) throws IOException {
		FileInputStream fis = new FileInputStream(new File(file));
		Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
		for (Element rootElement : doc.select(Constants.Query.TOP)) {
			DocumentQuery query = parseDoc(rootElement);
			queries.add(query);
		}
		fis.close();
		return queries;
	}

	private static DocumentQuery parseDoc(Element rootElement) {
		List<Element> elementList = rootElement.getAllElements();
		DocumentQuery query = new DocumentQuery();
		for (Element element : elementList) {
			buildModel(element, query);
		}
		return query;
	}

	private static void buildModel(Element element, DocumentQuery query) {
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
