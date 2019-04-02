package documents;

public class DocumentService {
	
	private DocumentDAO db = new DocumentCMS(); 
	
	public Document GetDocument(String id) {
		return db.GetDocument(id);
	}
	
}