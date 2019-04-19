package search;

import documents.Document;

public class Search {
	
	
	public Document[] results;
	public Paging paging;
	
	public Search() {}
	
	public Search(Document[] results, Paging paging) {
		this.results = results;
		this.paging = paging;
	}
 }
