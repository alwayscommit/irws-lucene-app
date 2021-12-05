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
				String re = item.replaceAll(
						"(?i)To be relevant, a document must discuss|also relevant|A relevant document identifies|To be relevant, a document must|The intent of this query|Relevant documents|is relevant|A relevant document must|Relevant items include|A relevant document will provide|A relevant document will discuss|A relevant document will provide information regarding|A relevant document could identify|A relevant document will provide information|Relevant documents will contain any information about the|A relevant document must discuss|A relevant document identifies|documents|document|Relevant documents will discuss the|a relevant document|a document will|to be relevant|relevant documents|a document must|will contain|will discuss|will provide|must cite",
						"");
				sentanceFilterList.add(re);
			} else {
				if(item.contains("unless")) {
					String re = item.replaceAll(
							"(?i)Documents that discuss|are irrelevant|irrelevant","");
					sentanceFilterList.add(re);
				}
			}
		}
		if(sentanceFilterList.size() > 0) {
			return String.join(". ", sentanceFilterList).trim();
		} else {
			return null;
		}
	}
	
	public void setNarrative(String narrative) {
		String actualNarrative = narrative.replaceAll("Narrative: |etc.", "");
		this.narrative = actualNarrative.trim();
	}
}
