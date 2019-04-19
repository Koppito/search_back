package documents;

public class DocumentService {
	
	private DocumentDAO db = new DocumentCMS(); 
	
	public Document getDocument(String id) {
		return db.GetDocument(id);
	}
	
}