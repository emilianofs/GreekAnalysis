package entities.words;

public class WordFormConjugatedDeclined extends WordFormConjugated{

	private WordFormDeclined.Gender gender;
	private WordFormDeclined.Case declension;
	
	public WordFormDeclined.Gender getGender() {
		return gender;
	}
	public void setGender(WordFormDeclined.Gender gender) {
		this.gender = gender;
	}
	public WordFormDeclined.Case getDeclension() {
		return declension;
	}
	public void setDeclension(WordFormDeclined.Case declension) {
		this.declension = declension;
	}
	
	
}
