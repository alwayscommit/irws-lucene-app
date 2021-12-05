package com.tcd.lucene.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.util.Constants;
import com.tcd.lucene.util.ParsingUtils;

public class FBISParser {

	public static void parse(String fbisPath, List<FBISDocument> fbisDocList) throws IOException {
		File fbisDirectory = new File(fbisPath);
		for (File file : fbisDirectory.listFiles()) {
			if (ParsingUtils.ignoreFile(file)) {
				continue;
			}
			FileInputStream fis = new FileInputStream(file);
			Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
			for (Element rootElement : doc.select(Constants.FBIS.DOC)) {
				FBISDocument fbisDoc = parseDoc(rootElement);
				fbisDocList.add(fbisDoc);
			}
			fis.close();
		}
	}

	private static FBISDocument parseDoc(Element rootElement) {
		List<Element> elementList = rootElement.getAllElements();
		FBISDocument fbisDoc = new FBISDocument();
		for (Element element : elementList) {
			buildModel(element, fbisDoc);
		}
		return fbisDoc;
	}

	private static void buildModel(Element element, FBISDocument fbisDoc) {
		switch (element.tagName()) {
		case Constants.FBIS.DOCNO:
			fbisDoc.setDocNo(element.ownText());
			break;
		case Constants.FBIS.HEADER:
			fbisDoc.setHeader(element.text());
			break;
		case Constants.FBIS.HT:
			fbisDoc.setHt(element.ownText());
			break;
		case Constants.FBIS.H3:
			fbisDoc.setH3(element.text());
			break;
		case Constants.FBIS.TEXT:
			fbisDoc.setText(element.text());
			break;
		case Constants.FBIS.F:
			String f = fbisDoc.getF() == null ? element.ownText() : (fbisDoc.getF() + "\n" + element.ownText());
			fbisDoc.setF(f);
			break;
		case Constants.FBIS.H4:
			fbisDoc.setH4(element.ownText());
			break;
		case Constants.FBIS.ABS:
			fbisDoc.setAbs(element.ownText());
			break;
		case Constants.FBIS.PHRASE:
			fbisDoc.setPhrase(element.ownText());
			break;
		}

	}
}
