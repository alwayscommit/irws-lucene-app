package com.tcd.lucene.parser;

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

public class FR94Parser {

	public static void parseNestedFolders(File[] files, List<FR94Document> fr94DocList) throws IOException {
		for (File file : files) {
			if (ignoreFile(file)) {
				System.out.println("Ignoring:: " + file.getName() );
				continue;
			}
			if (file.isDirectory()) {
				parseNestedFolders(file.listFiles(), fr94DocList);
			} else {
				FileInputStream fis = new FileInputStream(file);
				Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
				for (Element rootElement : doc.select(Constants.FBIS.DOC)) {
					FR94Document fr94Doc = parseDoc(rootElement);
					fr94DocList.add(fr94Doc);
				}
				fis.close();
			}
		}
	}

	// ignore file if it's .DS or readme or something read
	private static boolean ignoreFile(File file) {
		if (file.getName().startsWith("~")) {
			return true;
		} else {
			return false;
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
		case Constants.FR94.PARENT:
			fr94Doc.setParent(element.ownText());
			break;
		case Constants.FR94.TEXT:
			fr94Doc.setText(element.ownText());
			break;
		case Constants.FR94.USDEPT:
			fr94Doc.setText(element.ownText());
			break;
		case Constants.FR94.USBUREAU:
			fr94Doc.setUsBureau(element.ownText());
			break;
		case Constants.FR94.CFRNO:
			fr94Doc.setCfrNo(element.ownText());
			break;
		case Constants.FR94.RINDOCK:
			fr94Doc.setRindock(element.ownText());
			break;
		case Constants.FR94.AGENCY:
			fr94Doc.setAgency(element.ownText());
			break;
		case Constants.FR94.ACTION:
			fr94Doc.setAction(element.ownText());
			break;
		case Constants.FR94.SUMMARY:
			fr94Doc.setSummary(element.ownText());
			break;
		case Constants.FR94.DATE:
			fr94Doc.setDate(element.ownText());
			break;
		case Constants.FR94.ADDRESS:
			fr94Doc.setAddress(element.ownText());
			break;
		case Constants.FR94.FURTHER:
			fr94Doc.setFurther(element.ownText());
			break;
		case Constants.FR94.SUPPLEM:
			fr94Doc.setSupplem(element.ownText());
			break;
		case Constants.FR94.SIGNER:
			fr94Doc.setSigner(element.ownText());
			break;
		case Constants.FR94.SIGNJOB:
			fr94Doc.setSignJob(element.ownText());
			break;
		case Constants.FR94.FRFILING:
			fr94Doc.setFrFiling(element.ownText());
			break;
		case Constants.FR94.BILLING:
			fr94Doc.setBilling(element.ownText());
			break;
		case Constants.FR94.TABLE:
			fr94Doc.setTable(element.ownText());
			break;
		}
	}

}
