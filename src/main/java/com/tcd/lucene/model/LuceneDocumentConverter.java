package com.tcd.lucene.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.tcd.lucene.util.Constants;

public class LuceneDocumentConverter {

	private static final String LINE_BREAK = "\n";

	public static List<Document> convertFBIS(List<FBISDocument> fbisDocuments) throws IllegalAccessException {
		List<Document> luceneDocuments = new ArrayList<Document>();
		for (FBISDocument fbisDocument : fbisDocuments) {
			Document doc = new Document();
			doc.add(new StringField(Constants.LuceneDocument.DOCUMENT_ID, fbisDocument.getDocNo(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.BODY, fbisDocument.getText(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.HEADERS, fbisDocument.getAbs() + LINE_BREAK + fbisDocument.getF() + LINE_BREAK + fbisDocument.getH3() + LINE_BREAK + fbisDocument.getHt()
					+ LINE_BREAK + fbisDocument.getH4() + LINE_BREAK + fbisDocument.getPhrase(), Field.Store.YES));
			luceneDocuments.add(doc);
		}
		return luceneDocuments;
	}

	public static List<Document> convertFR94(List<FR94Document> fr94Documents) throws IllegalAccessException {
		List<Document> luceneDocuments = new ArrayList<Document>();
		for (FR94Document fr94Document : fr94Documents) {
			Document doc = new Document();
			doc.add(new StringField(Constants.LuceneDocument.DOCUMENT_ID, fr94Document.getDocno(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.BODY, fr94Document.getText() + LINE_BREAK + fr94Document.getSupplem(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.HEADERS, fr94Document.getFootnote() + LINE_BREAK + fr94Document.getUsDept() + LINE_BREAK + fr94Document.getSummary(), Field.Store.YES));
			luceneDocuments.add(doc);
		}
		return luceneDocuments;
	}

	public static List<Document> convertFT(List<FTDocument> ftDocuments) throws IllegalAccessException {
		List<Document> luceneDocuments = new ArrayList<Document>();
		for (FTDocument ftDocument : ftDocuments) {
			Document doc = new Document();
			doc.add(new StringField(Constants.LuceneDocument.DOCUMENT_ID, ftDocument.getDocno(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.BODY, ftDocument.getText() != null ? ftDocument.getText() : "", Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.HEADERS, ftDocument.getHeadline() + LINE_BREAK + ftDocument.getTp() + LINE_BREAK + ftDocument.getPub() + LINE_BREAK + ftDocument.getXx(),
					Field.Store.YES));
			luceneDocuments.add(doc);
		}
		return luceneDocuments;
	}

	public static List<Document> convertLATimes(List<LATimesDocument> laTimesDocuments) throws IllegalAccessException {
		List<Document> luceneDocuments = new ArrayList<Document>();
		for (LATimesDocument laTimesDocument : laTimesDocuments) {
			Document doc = new Document();
			doc.add(new StringField(Constants.LuceneDocument.DOCUMENT_ID, laTimesDocument.getDocNo(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.BODY, laTimesDocument.getText() + LINE_BREAK + laTimesDocument.getCorrection(), Field.Store.YES));
			doc.add(new TextField(Constants.LuceneDocument.HEADERS, laTimesDocument.getHeadline() + LINE_BREAK + laTimesDocument.getSubject() + LINE_BREAK + laTimesDocument.getDateline(),
					Field.Store.YES));
			luceneDocuments.add(doc);
		}
		return luceneDocuments;
	}

}
