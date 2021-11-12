package com.tcd.lucene.model;

public class FR94Document {

	// [TEXT, FOOTNOTE, SUMMARY, DOC, DOCNO, USDEPT, SUPPLEM]

	private String text = "";
	private String footnote = "";
	private String summary = "";
	private String doc = "";
	private String usDept = "";
	private String supplem = "";
	private String docno = "";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getUsDept() {
		return usDept;
	}

	public void setUsDept(String usDept) {
		this.usDept = usDept;
	}

	public String getSupplem() {
		return supplem;
	}

	public void setSupplem(String supplem) {
		this.supplem = supplem;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

}
