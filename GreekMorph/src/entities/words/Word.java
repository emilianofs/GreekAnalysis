package entities.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;

public class Word {

	//	private WordLemma lemma; SACAR
//	public List<Map<String,String>> result = new ArrayList<Map<String,String>>();
//	public String lemmaString;
//	
	public DiscourseFragment fragment;
	protected String word_betaCode;
	protected String word_UTF8;
//	
//	private WordLemma lemma;
	protected List<String> features = new ArrayList<String>();
	protected List<String> dialects = new ArrayList<String>();
	protected List<WordForm> wordForms = new ArrayList<WordForm>();
	

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public List<String> getDialects() {
		return dialects;
	}

	public void setDialects(List<String> dialects) {
		this.dialects = dialects;
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
}
