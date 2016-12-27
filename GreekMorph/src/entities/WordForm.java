package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unc.epidoc.transcoder.TransCoder;

public class WordForm{
	public String originalForm;
	public String matchForm; 
	public String lemma;
	public List<Map<String,String>> result = new ArrayList<Map<String,String>>();

	public WordForm(String form){
		try{
			originalForm = form;

			TransCoder tc = new TransCoder();
			tc.setParser("Unicode");
			tc.setConverter("BetaCode");
			matchForm = tc.getString(form);

//			System.out.println(matchForm);
			matchForm = matchForm.toLowerCase();
			matchForm = matchForm.replace("\\", "/");
//			System.out.println(matchForm);
		}catch(Exception e){

		}
	}
}