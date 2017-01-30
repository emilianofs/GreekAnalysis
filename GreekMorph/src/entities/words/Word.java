package entities.words;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import edu.unc.epidoc.transcoder.TransCoder;
@Entity
@Table(name="words")
public class Word {

	//	private WordLemma lemma; SACAR
//	public List<Map<String,String>> result = new ArrayList<Map<String,String>>();
//	public String lemmaString;
//	
	@Column(name="wordBetaCode")
	protected String word_betaCode;
	
	@Column(name="wordUTF8")
	protected String word_UTF8;
//	
	private WordLemma lemma;
	
	@ElementCollection
	@CollectionTable(name="features", joinColumns=@JoinColumn(name="user_id"))
	@Column(name="nickname")
	protected List<String> features = new ArrayList<String>();
//	protected List<String> dialects = new ArrayList<String>();
	@OneToMany(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	protected List<WordForm> wordForms = new ArrayList<WordForm>();
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	protected Language language;

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public List<WordForm> getWordForms() {
		return wordForms;
	}

	public void setWordForms(List<WordForm> wordForms) {
		this.wordForms = wordForms;
	}
	public String getWord_betaCode() {
		return word_betaCode;
	}

	public void setWord_betaCode(String word_betaCode) {
		this.word_betaCode = word_betaCode;
	}

	public String getWord_UTF8() {
		return word_UTF8;
	}

	public void setWord_UTF8(String word_UTF8) {
		this.word_UTF8 = word_UTF8;
	}

	public WordLemma getLemma() {
		return lemma;
	}

	public void setLemma(WordLemma lemma) {
		this.lemma = lemma;
	}

	public Word(){

	}

	public Word(String form){
		try{
			word_betaCode = form;

			TransCoder tc = new TransCoder();
			tc.setParser("Unicode");
			tc.setConverter("BetaCode");
			word_UTF8 = tc.getString(form);

			//			System.out.println(matchForm);
			word_UTF8 = word_UTF8.toLowerCase();
			word_UTF8 = word_UTF8.replace("\\", "/");
			//			System.out.println(matchForm);
		}catch(Exception e){

		}
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
}
