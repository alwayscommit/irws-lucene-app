package com.tcd.lucene.model;

public class DocumentQuery {
	private String queryNumber = "";
	private String queryTitle = "";
	private String description = "";
	private String narrative = "";
	
	public String getQueryNumber() {
		return queryNumber;
	}
	
	public void setQueryNumber(String queryNumber) {
		String number = queryNumber.replaceAll("Number: ", "");
		this.queryNumber = number;
	}
	
	public String getQueryTitle() {
		return queryTitle;
	}
	
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		String actualDescription = description.replaceAll("Description: ", "");
		this.description = actualDescription.trim();
	}
	
	public String getNarrative() {
		return narrative;
	}
	
	public void setNarrative(String narrative) {
		String actualNarrative = narrative.replaceAll("Narrative: ", "");
		this.narrative = actualNarrative.trim();
	}
}
