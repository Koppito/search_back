package index;

import java.io.Serializable;

public class Word implements Serializable {
	
	private static final long serialVersionUID = -2850128185627889414L;
	public String word;
	public int nr;
	public int termFrequency;
	
	public Word(String word, int nr, int termFrequency) {
		this.word = word;
		this.nr = nr;
		this.termFrequency = termFrequency;
	}
	
}
