package com.tcd.lucene.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.tcd.lucene.model.FBISDocument;
import com.tcd.lucene.util.Constants;

public class FBISParser {

	public static List<FBISDocument> parse(String fbisPath) throws IOException {
		File fbisDirectory = new File(fbisPath);
		List<FBISDocument> fbisDocList = new ArrayList<FBISDocument>();
		for (File file : fbisDirectory.listFiles()) {
			FileInputStream fis = new FileInputStream(file);
			Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
			for (Element rootElement : doc.select(Constants.FBIS.DOC)) {
				FBISDocument fbisDoc = parseDoc(rootElement);
				fbisDocList.add(fbisDoc);
			}
			fis.close();
		}
		return fbisDocList;
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
		case Constants.FBIS.DATE1:
			fbisDoc.setDate1(element.text());
			break;
		case Constants.FBIS.DOCNO:
			fbisDoc.setDocNo(element.text());
			break;
		case Constants.FBIS.HT:
			fbisDoc.setHt(element.text());
			break;
		case Constants.FBIS.HEADER:
			fbisDoc.setHeader(element.text());
			break;
		case Constants.FBIS.H2:
			fbisDoc.setH2(element.text());
			break;
		case Constants.FBIS.H3:
			fbisDoc.setH3(element.text());
			break;
		case Constants.FBIS.TI:
			fbisDoc.setTi(element.text());
			break;
		case Constants.FBIS.TEXT:
			fbisDoc.setText(element.text());
			break;
		case Constants.FBIS.TXT5:
			fbisDoc.setTxt5(element.text());
			break;
		case Constants.FBIS.FIG:
			fbisDoc.setFig(element.text());
			break;
		case Constants.FBIS.F:
			String f = fbisDoc.getF() == null ? element.text() : (fbisDoc.getF() + "\n" + element.text());
			fbisDoc.setF(f);
			break;
		case Constants.FBIS.H4:
			fbisDoc.setH4(element.text());
			break;
		case Constants.FBIS.H5:
			fbisDoc.setH5(element.text());
			break;
		case Constants.FBIS.H6:
			fbisDoc.setH6(element.text());
			break;
		case Constants.FBIS.H7:
			fbisDoc.setH7(element.text());
			break;
		case Constants.FBIS.H8:
			fbisDoc.setH8(element.text());
			break;
		case Constants.FBIS.ABS:
			fbisDoc.setAbs(element.text());
			break;
		case Constants.FBIS.AU:
			fbisDoc.setAu(element.text());
			break;
		case Constants.FBIS.PHRASE:
			fbisDoc.setPhrase(element.text());
			break;
		case Constants.FBIS.TR:
			fbisDoc.setTr(element.text());
			break;
		}

	}
}
