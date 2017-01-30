package entities.words;

import java.util.ArrayList;
import java.util.List;

public class WordForm {
	/*
	<number>dual</number>
	<number>pl</number>
	<number>sg</number>
	 */
//	private WordLemma lemma;
	private List<WordDialect> dialects = new ArrayList<WordDialect>();
	private List<String> features = new ArrayList<String>();
	
	public enum Number{
		DUAL,
		PLURAL,
		SINGULAR;

		public static Number fromString(String text) {
			if (text != null) {
				switch(text){
				case "dual":
					return Number.DUAL;
				case "pl":
					return Number.PLURAL;
				case "sg":
					return Number.SINGULAR;
				}
			}
			return null;
		}
	}

	/*
	<pos>adj</pos>
	<pos>adverbial</pos>
	<pos>adv</pos>
	<pos>article</pos>
	<pos>conj</pos>
	<pos>exclam</pos>
	<pos>irreg</pos>
	<pos>noun</pos>
	<pos>numeral</pos>
	<pos>partic</pos>
	<pos>part</pos>
	<pos>prep</pos>
	<pos>pron</pos>
	<pos>verb</pos>
	 */
	private Position position;
	public enum Position{
		ADJETIVE,
		ADVERBIAL,
		ADVERBE,
		ARTICLE,
		CONJUNCTION,
		EXCLAMATION,
		IRREGULAR,
		NOUN,
		NUMERAL,
		PARTICLE,
		PARTICIPLE,
		PREPOSITION,
		PRONOUN,
		VERB;

		public static Position fromString(String text) {
			if (text != null) {
				switch(text){
				case "adj":
					return Position.ADJETIVE;
				case "adverbial":
					return Position.ADVERBIAL;
				case "adv":
					return Position.ADVERBE;
				case "article":
					return Position.ARTICLE;
				case "conj":
					return Position.CONJUNCTION;
				case "exclam":
					return Position.EXCLAMATION;
				case "irreg":
					return Position.IRREGULAR;
				case "noun":
					return Position.NOUN;
				case "numeral":
					return Position.NUMERAL;
				case "partic":
					return Position.PARTICLE;
				case "part":
					return Position.PARTICIPLE;
				case "prep":
					return Position.PREPOSITION;
				case "pron":
					return Position.PRONOUN;
				case "verb":
					return Position.VERB;
				}
			}
			return null;
		}
	}


//	
//	public Number getNumber() {
//		return number;
//	}
//
//	public void setNumber(Number number) {
//		this.number = number;
//	}


	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public List<WordDialect> getDialects() {
		return dialects;
	}

	public void setDialects(List<WordDialect> dialects) {
		this.dialects = dialects;
	}

//	public WordLemma getLemma() {
//		return lemma;
//	}
//
//	public void setLemma(WordLemma lemma) {
//		this.lemma = lemma;
//	}

}
