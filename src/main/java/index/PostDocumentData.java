package index;

import java.io.Serializable;

public class PostDocumentData implements Serializable {
	
	public String document;
	public int termFrequency;
	
	public PostDocumentData(String document, int termFrequency) {
		this.document = document;
		this.termFrequency = termFrequency;
	}
	
	public String toString() {
		return String.format("PostDocumentData(Document: %s, Term Frequency: %d)", 
				this.document, this.termFrequency);
	}
	
}
