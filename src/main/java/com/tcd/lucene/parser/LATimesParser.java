package com.tcd.lucene.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.LATimesDocument;
import com.tcd.lucene.util.Constants;
import com.tcd.lucene.util.ParsingUtils;

public class LATimesParser {

	public static void parse(String latimesPath, List<LATimesDocument> laTimesDocList) throws IOException {
		File laDirectory = new File(latimesPath);
		for (File file : laDirectory.listFiles()) {
			if (ParsingUtils.ignoreFile(file)) {
				continue;
			}
			FileInputStream fis = new FileInputStream(file);
			Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());

			for (Element rootElement : doc.select(Constants.FBIS.DOC)) {
				LATimesDocument laTimesDoc = parseDoc(rootElement);
				laTimesDocList.add(laTimesDoc);
			}
			fis.close();
		}
	}

	private static LATimesDocument parseDoc(Element rootElement) {
		List<Element> elementList = rootElement.getAllElements();
		LATimesDocument laTimesDoc = new LATimesDocument();
		for (Element element : elementList) {
			buildModel(element, laTimesDoc);
		}
		return laTimesDoc;
	}

	private static void buildModel(Element element, LATimesDocument laTimesDoc) {
		switch (element.tagName()) {
		case Constants.LATimes.TEXT:
			laTimesDoc.setText(element.text());
			break;
		case Constants.LATimes.DATELINE:
			laTimesDoc.setDateline(element.ownText());
			break;
		case Constants.LATimes.DOCID:
			laTimesDoc.setDocid(element.ownText());
			break;
		case Constants.LATimes.CORRECTION:
			laTimesDoc.setCorrection(element.ownText());
			break;
		case Constants.LATimes.SUBJECT:
			laTimesDoc.setSubject(element.ownText());
			break;
		case Constants.LATimes.DOCNO:
			laTimesDoc.setDocNo(element.ownText());
			break;
		case Constants.LATimes.HEADLINE:
			laTimesDoc.setHeadline(element.ownText());
			break;
		}
	}

}
