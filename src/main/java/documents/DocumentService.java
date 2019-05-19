package documents;

public class DocumentService {
	
	private DocumentDAO db = new DocumentCMS(); 
	
	public Document getDocument(String id) {
		return db.GetDocument(id, -1);
	}
	
	public String getDocumentSample(String id, int limit) {
		return db.GetDocument(id, limit).text;
	}
	
}