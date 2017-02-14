package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.unc.epidoc.transcoder.TransCoder;
import entities.words.Word;

public class DiscourseFragment {
	private String number;
	private String text;

	private Map<String, Word> morph = new LinkedHashMap<String, Word>();
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Map<String, Word> getMorph() {
		return morph;
	}
	public void setMorph(Map<String, Word> morph) {
		this.morph = morph;
	}
	public List<String> getWordList() throws Exception{
		List<String> response = new ArrayList<String>();

		TransCoder tc = new TransCoder();
		tc.setParser("Unicode");
		tc.setConverter("BetaCode");
		String textBeta = tc.getString(text);
		
//		System.out.println(text);
//		System.out.println(textBeta);
		textBeta = textBeta.toLowerCase();
//		System.out.println(textBeta);
		String[] wordList = textBeta.split(" ");
		List<String> itemList = Arrays.asList(wordList);
		for(String word: itemList){
//			System.out.println("pre "+word);
			word = word.replace(" ", "");
			word = word.replace(".", "");
			word = word.replace(",", "");
			word = word.replace(":", "");
			word = word.replace("·", "");
			word = word.replace(";", "");
//			word = tc.getString(word);
			word = word.replace("\\","/");
//			word = word.toLowerCase();
//			System.out.println("post "+word);
			response.add(word);
		}
		return response;
	}


}
