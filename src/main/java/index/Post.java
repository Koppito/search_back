package index;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Post implements Serializable {
	
	private static final long serialVersionUID = -1267212724232946255L;
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
