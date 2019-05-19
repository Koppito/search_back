package search;

import java.util.ArrayList;
import java.util.HashMap;

import documents.Document;
import documents.DocumentService;
import index.IndexManager;
import index.Post;
import index.PostDao;
import index.PostDocumentData;
import index.Word;

public class SearchService {
	
	public static final int R = 5;
	public static final int totalDocs = 2;
	
	public Search searchDocuments(String query, int offset, int limit) {
		String[] queries = query
				.replaceAll("[^A-Za-z]+", " ")
				.toLowerCase()
				.split("\\s");
		
		HashMap<String, Word> vocabulary = IndexManager.getVocabulary();
		
		// Get all Words from vocabulary and sort reverse by nr
		ArrayList<Word> words = new ArrayList<Word>();
		for (String q : queries) {
			Word w = vocabulary.get(q);
			if (w != null)
				words.add(w);
		}
		words.sort((x, y) -> {
			if (x.nr == y.nr) return 0;
			return (x.nr < y.nr) ? 1 : -1; 
		});
		
		// For each word get R candidate documents from post
		HashMap<String, DocumentRank> candidates = new HashMap<String, DocumentRank>();
		PostDao dao = PostDao.getInstance();
		for (Word w : words) {
			Post post = dao.getPost(w.word);
			
			for (int x = 0; x < post.documents.length && x < limit; x++) {
				DocumentRank existing = candidates.get(post.documents[x].document);
				if (existing != null) {
					existing.addRank(w);
					candidates.put(post.documents[x].document, existing);
					continue;
				} 
				
				DocumentRank newRank = new DocumentRank(post.documents[x]);
				newRank.addRank(w);
				candidates.put(post.documents[x].document, newRank);
			}
		}
		
		ArrayList<DocumentRank> candidatesToOrder = new ArrayList<DocumentRank>();
		candidatesToOrder.addAll(candidates.values());
		candidatesToOrder.sort((x, y) -> {
			if (x.rank == y.rank) return 0;
			return (x.rank > y.rank) ? -1 : 1;
		});
		
		ArrayList<Document> results = new ArrayList<Document>();
		for (DocumentRank d : candidatesToOrder) {
			String docID = d.docData.document.replace(".txt", "");
			results.add(new Document(docID, new DocumentService().getDocumentSample(docID, 5)));
		}
		
		int start = (offset > results.size()) ? results.size() : offset;
		int end = ((offset + limit) > results.size()) ? results.size() : (offset + limit);
		
		return new Search(results.subList(start, end), new Paging(offset, limit, results.size()));
	}
	
	private class DocumentRank {
		public PostDocumentData docData;
		public double rank;
		
		public DocumentRank(PostDocumentData docData) {
			this.rank = 0;
			this.docData = docData;
		}
		
		// addRank Adds word's weight to document rank
		public void addRank(Word word) {
			// Formulae -> tfr * log(N/nr)
			
			this.rank += word.termFrequency * Math.log(totalDocs/word.nr);
		}
		
		@Override
		public String toString() {
			return String.format("Rank(%s, %f)", this.docData.document, this.rank);
		}
	}
}
