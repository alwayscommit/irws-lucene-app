package com.tcd.lucene.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.util.Constants;
import com.tcd.lucene.util.ParsingUtils;

public class FR94Parser {

	public static void parseNestedFolders(File[] files, List<FR94Document> fr94DocList) throws IOException {
		for (File file : files) {
			if (ParsingUtils.ignoreFile(file)) {
				continue;
			}
			if (file.isDirectory()) {
				parseNestedFolders(file.listFiles(), fr94DocList);
			} else {
				FileInputStream fis = new FileInputStream(file);
				Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
				for (Element rootElement : doc.select(Constants.FR94.DOC)) {
					FR94Document fr94Doc = parseDoc(rootElement);
					fr94DocList.add(fr94Doc);
				}
				fis.close();
			}
		}
	}

	private static FR94Document parseDoc(Element rootElement) {
		List<Element> elementList = rootElement.getAllElements();
		FR94Document fr94Doc = new FR94Document();
		for (Element element : elementList) {
			buildModel(element, fr94Doc);
		}
		return fr94Doc;
	}

	private static void buildModel(Element element, FR94Document fr94Doc) {
		switch (element.tagName()) {
		case Constants.FR94.DOCNO:
			fr94Doc.setDocno(element.ownText());
			break;
		case Constants.FR94.TEXT:
			fr94Doc.setText(element.text());
			break;
		case Constants.FR94.USDEPT:
			fr94Doc.setText(element.ownText());
			break;
		case Constants.FR94.SUMMARY:
			fr94Doc.setSummary(element.ownText());
			break;
		case Constants.FR94.SUPPLEM:
			fr94Doc.setSupplem(element.ownText());
			break;
		case Constants.FR94.FOOTNOTE:
			fr94Doc.setFootnote(element.ownText());
			break;
		}
	}

}
