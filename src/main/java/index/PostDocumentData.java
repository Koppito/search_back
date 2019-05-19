package index;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PostDocumentData implements Serializable, Comparable {
	
	private static final long serialVersionUID = 4797629461086614797L;
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
	
	public int getTermFrequency() {
		return termFrequency;
	}

	@Override
	public int compareTo(Object o) {
		PostDocumentData pd = (PostDocumentData) o;
		if (pd.termFrequency == this.termFrequency) return 0; 
		return (this.termFrequency > pd.termFrequency) ? -1 : 1;
	}
	
}
