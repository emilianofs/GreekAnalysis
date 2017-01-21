package entities.words;

public class WordFormDeclined extends WordForm {

	private Number number;
	
	/*
 	<gender>fem</gender>
	<gender>masc</gender>
	<gender>neut</gender>
	 */
	private Gender gender;
	public enum Gender{
		MASCULINE,
		FEMENINE,
		NEUTER;
		
		public static Gender fromString(String text) {
			if (text != null) {
				switch(text){
				case "fem":
					return Gender.FEMENINE;
				case "masc":
					return Gender.MASCULINE;
				case "neut":
					return Gender.NEUTER;
				}
			}
			return null;
		}
	}
	
	
	/*
		<case>acc</case>
		<case>dat</case>
		<case>gen</case>
		<case>nom</case>
		<case>voc</case>
	 */
	private Case declension;
	public enum Case{
		NOMINATIVE,
		VOCATIVE,
		ACUSATIVE,
		GENITIVE,
		DATIVE;
		
		public static Case fromString(String text) {
			if (text != null) {
				switch(text){
				case "nom":
					return Case.NOMINATIVE;
				case "voc":
					return Case.VOCATIVE;
				case "acc":
					return Case.ACUSATIVE;
				case "gen":
					return Case.GENITIVE;
				case "dat":
					return Case.DATIVE;

				}
			}
			return null;
		}
	}
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Case getDeclension() {
		return declension;
	}
	public void setDeclension(Case declension) {
		this.declension = declension;
	}
	public Number getNumber() {
		return number;
	}
	public void setNumber(Number number) {
		this.number = number;
	}
	
}
