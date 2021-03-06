package entities.words;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

public class WordDialect {
	//<dialect>attic epic doric ionic aeolic parad_form prose poetic</dialect>

	public enum Dialect{
		ATTIC,
		EPIC,
		DORIC,
		IONIC,
		AEOLIC,
		PARAD_FORM,
		PROSE,
		POETIC,
		HOMERIC;
		
		public static Dialect fromString(String text) {
			if (text != null) {
				switch(text){
				case "attic":
					return Dialect.ATTIC;
				case "epic":
					return Dialect.EPIC;
				case "doric":
					return Dialect.DORIC;
				case "ionic":
					return Dialect.IONIC;
				case "aeolic":
					return Dialect.AEOLIC;
				case "parad_form":
					return Dialect.PARAD_FORM;
				case "prose":
					return Dialect.PROSE;
				case "poetic":
					return Dialect.POETIC;
				case "homeric":
					return Dialect.HOMERIC;
				}
			}
			return null;
		}
	}
	@Enumerated(EnumType.STRING)
	private Dialect dialect;
	
	@OneToMany(mappedBy="dialect")
	private List<WordForm> words = new ArrayList<WordForm>();
	
	/************/
	public Dialect getDialect() {
		return dialect;
	}
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
	public List<WordForm> getWords() {
		return words;
	}
	public void setWords(List<WordForm> words) {
		this.words = words;
	}
	
}
