package com.tcd.lucene.model;

public class FBISDocument {
	
//	F,H3,H4, ABS, HT, PHRASE -> HEADER
//	TEXT, -> BODY
//	DOC NO, -> DOC NO

	private String doc="";
	private String docNo="";
	private String phrase="";
	private String abs="";
	private String ht="";
	private String h3="";
	private String h4="";
	private String f="";
	private String text="";

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getHt() {
		return ht;
	}

	public void setHt(String ht) {
		this.ht = ht;
	}

	public String getH3() {
		return h3;
	}

	public void setH3(String h3) {
		this.h3 = h3;
	}

	public String getH4() {
		return h4;
	}

	public void setH4(String h4) {
		this.h4 = h4;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}