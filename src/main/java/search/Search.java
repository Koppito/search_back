package search;

import java.util.List;

import documents.Document;

public class Search {

	public List<Document> results;
	public Paging paging;
	
	public Search() {}
	
	public Search(List<Document> results, Paging paging) {
		this.results = results;
		this.paging = paging;
	}
 }
