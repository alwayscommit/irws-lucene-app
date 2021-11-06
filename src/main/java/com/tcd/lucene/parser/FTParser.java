package com.tcd.lucene.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.FR94Document;
import com.tcd.lucene.model.FTDocument;
import com.tcd.lucene.util.Constants;
import com.tcd.lucene.util.ParsingUtils;

public class FTParser {

	public static void parseNestedFolders(File[] files, List<FTDocument> ftDocList) throws IOException {
		for (File file : files) {
			if (ParsingUtils.ignoreFile(file)) {
				continue;
			}
			if (file.isDirectory()) {
				parseNestedFolders(file.listFiles(), ftDocList);
			} else {
				FileInputStream fis = new FileInputStream(file);
				Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
				for (Element rootElement : doc.select(Constants.FT.DOC)) {
					FTDocument ftDoc = parseDoc(rootElement);
					ftDocList.add(ftDoc);
				}
				fis.close();
			}
		}
	}

	private static FTDocument parseDoc(Element rootElement) {
		List<Element> elementList = rootElement.getAllElements();
		FTDocument ftDoc = new FTDocument();
		for (Element element : elementList) {
			buildModel(element, ftDoc);
		}
		return ftDoc;
	}

	private static void buildModel(Element element, FTDocument ftDoc) {
		switch (element.tagName()) {
		case Constants.FT.XX:
			ftDoc.setXx(element.ownText());
			break;
		case Constants.FT.BYLINE:
			ftDoc.setByLine(element.ownText());
			break;
		case Constants.FT.IN:
			ftDoc.setIn(element.ownText());
			break;
		case Constants.FT.TEXT:
			ftDoc.setText(element.ownText());
			break;
		case Constants.FT.PAGE:
			ftDoc.setText(element.ownText());
			break;
		case Constants.FT.CN:
			ftDoc.setText(element.ownText());
			break;
		case Constants.FT.CO:
			ftDoc.setText(element.ownText());
			break;
		case Constants.FT.DATELINE:
			ftDoc.setText(element.ownText());
			break;
		case Constants.FT.DATE:
			ftDoc.setDate(element.ownText());
			break;
		case Constants.FT.PROFILE:
			ftDoc.setProfile(element.ownText());
			break;
		case Constants.FT.PE:
			ftDoc.setPe(element.ownText());
			break;
		case Constants.FT.DOC:
			ftDoc.setDoc(element.ownText());
			break;
		case Constants.FT.DOCNO:
			ftDoc.setDocno(element.ownText());
			break;
		case Constants.FT.HEADLINE:
			ftDoc.setHeadline(element.ownText());
			break;
		case Constants.FT.PUB:
			ftDoc.setPub(element.ownText());
			break;
		case Constants.FT.TP:
			ftDoc.setTp(element.ownText());
			break;
		}

	}

}
