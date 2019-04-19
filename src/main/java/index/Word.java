package index;

import java.io.Serializable;

public class Word implements Serializable {
	
	public String word;
	public int nr;
	public int termFrequency;
	
	public Word(String word, int nr, int termFrequency) {
		this.word = word;
		this.nr = nr;
		this.termFrequency = termFrequency;
	}
	
}
