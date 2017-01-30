package entities.words;

import java.util.ArrayList;
import java.util.List;

public class Language {

	private LanguageName name;
	
	public enum LanguageName{
		GREEK,
		LATIN;
		
		public static LanguageName fromString(String text) {
			if (text != null) {
				switch(text){
				case "greek":
					return LanguageName.GREEK;
				case "latin":
					return LanguageName.LATIN;

				}
			}
			return null;
		}
	}
	
	private List<Word> words = new ArrayList<Word>();
	private List<WordLemma> lemmas = new ArrayList<WordLemma>();
	private List<WordDialect> dialects = new ArrayList<WordDialect>();
	
	/************/

	public LanguageName getName() {
		return name;
	}

	public void setName(LanguageName name) {
		this.name = name;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public List<WordLemma> getLemmas() {
		return lemmas;
	}

	public void setLemmas(List<WordLemma> lemmas) {
		this.lemmas = lemmas;
	}

	public List<WordDialect> getDialects() {
		return dialects;
	}

	public void setDialects(List<WordDialect> dialects) {
		this.dialects = dialects;
	}
	
	

}
