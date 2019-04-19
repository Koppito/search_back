package search;

import documents.Document;

public class SearchService {
	
	public Search searchDocuments(String query) {
		Document[] results = new Document[5];
		for (int x = 0; x < 5; x++) {
			results[x] = new Document("123", "asidjia");
		}
		
		return new Search(results, new Paging(2, 2));
	}
	
}
