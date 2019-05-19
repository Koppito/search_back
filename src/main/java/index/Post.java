package index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import util.ArrayUtils;

@Entity
public class Post implements Serializable {
	
	private static final long serialVersionUID = -1267212724232946255L;
	public String word;
	public PostDocumentData[] documents = new PostDocumentData[1];
	
	public Post(String word, PostDocumentData[] documents) {
		this.word = word;
		this.documents = documents;
	}
	
	public String toString() {
		String p = Arrays.toString(this.documents);
		return String.format("Post(Word: %s, Documents: %s)", this.word, p);
	}
	
	public void addDocument(PostDocumentData pd) {
		ArrayList<PostDocumentData> l = (ArrayList<PostDocumentData>) ArrayUtils.convertArrayToList(this.documents);
		l.add(pd);
		PostDocumentData[] p = new PostDocumentData[l.size()];
		p = l.toArray(p);
		this.documents = p;
	}
	
}
