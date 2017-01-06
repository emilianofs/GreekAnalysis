package entities.words;

import java.util.List;

public class WordLemma {
	public String lemma;
	public List<Word> elements;
	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public List<Word> getElements() {
		return elements;
	}
	public void setElements(List<Word> elements) {
		this.elements = elements;
	}
	
}
