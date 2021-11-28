package com.tcd.lucene.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public String getActualNarrative() {
		List<String> list = Arrays.asList(this.narrative.split("\\.\\s+"));
		List<String> sentanceFilterList = new ArrayList<String>();
		for (String item: list) {
			if(!item.contains("not relevant") && !item.contains("irrelevant")) {
//				System.out.println(item);
				sentanceFilterList.add(item);
			}
		}
		if(sentanceFilterList.size() > 0) {
			String a = String.join(". ", sentanceFilterList).trim();
			System.out.println("Query ID  "+ queryNumber);
			System.out.println(a);
			return String.join(". ", sentanceFilterList).trim();
		} else {
			return "";
		}
	}
	
	public void setNarrative(String narrative) {
		String actualNarrative = narrative.replaceAll("Narrative: |etc.", "");
		this.narrative = actualNarrative.trim();
	}
}
