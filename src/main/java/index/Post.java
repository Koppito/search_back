package index;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {
	
	public String word;
	public List<PostDocumentData> documents;
	
	public Post(String word, List<PostDocumentData> documents) {
		this.word = word;
		this.documents = documents;
	}
	
	public String toString() {
		return String.format("Post(Word: %s)", this.word);
	}
	
}
