package entities.words;

public class WordFormConjugated extends WordForm{
	
	private Number number;

	/*
	<person>1st</person>
	<person>2nd</person>
	<person>3rd</person>
	 */
	private Person person;
	public enum Person{
		FIRST,
		SECOND,
		THIRD;
		
		public static Person fromString(String text) {
			if (text != null) {
				switch(text){
				case "1st":
					return Person.FIRST;
				case "2nd":
					return Person.SECOND;
				case "3rd":
					return Person.THIRD;
				}
			}
			return null;
		}
	}
	
	/*
		<tense>aor</tense>
		<tense>futperf</tense>
		<tense>fut</tense>
		<tense>imperf</tense>
		<tense>perf</tense>
		<tense>plup</tense>
		<tense>pres</tense>
	 */
	private Tense tense;
	public enum Tense{
		AORISTOS,
		FUTURE_PERFECT,
		FUTURE,
		IMPERFECT,
		PERFECT,
		PLUSCUAMPERFECT,
		PRESENT;
		
		public static Tense fromString(String text) {
			if (text != null) {
				switch(text){
				case "aor":
					return Tense.AORISTOS;
				case "futperf":
					return Tense.FUTURE_PERFECT;
				case "fut":
					return Tense.FUTURE;
				case "imperf":
					return Tense.IMPERFECT;
				case "perf":
					return Tense.PERFECT;
				case "plup":
					return Tense.PLUSCUAMPERFECT;
				case "pres":
					return Tense.PRESENT;
				
				}
			}
			return null;
		}
	}
	/*
	<mood>imperat</mood>
	<mood>ind</mood>
	<mood>inf</mood>
	<mood>opt</mood>
	<mood>subj</mood>	
	 */
	private Mode mode;
	public enum Mode{
		IMPERATIVE,
		INDICATIVE,
		INFINITIVE,
		OPTATIVE,
		SUBJUNTIVE;
		
		public static Mode fromString(String text) {
			if (text != null) {
				switch(text){
				case "imperat":
					return Mode.IMPERATIVE;
				case "ind":
					return Mode.INDICATIVE;
				case "inf":
					return Mode.INFINITIVE;
				case "opt":
					return Mode.OPTATIVE;
				case "subj":
					return Mode.SUBJUNTIVE;				
				}
			}
			return null;
		}
	}
	/*
	<voice>act</voice>
	<voice>mid</voice>
	<voice>mp</voice>
	<voice>pass</voice>
	 */
	private Voice voice;
	public enum Voice{
		ACTIVE,
		PASSIVE,
		MIDPASSIVE;
		
		public static Voice fromString(String text) {
			if (text != null) {
				switch(text){
				case "act":
					return Voice.ACTIVE;
				case "pass":
					return Voice.PASSIVE;
				case "mp":
				case "mid":
					return Voice.MIDPASSIVE;				
				}
			}
			return null;
		}
		
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Tense getTense() {
		return tense;
	}
	public void setTense(Tense tense) {
		this.tense = tense;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public Voice getVoice() {
		return voice;
	}
	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	public Number getNumber() {
		return number;
	}
	public void setNumber(Number number) {
		this.number = number;
	}
	
	
	
}
