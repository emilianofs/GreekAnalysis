package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.unc.epidoc.transcoder.TransCoder;

public class GroupMatchHandler extends DefaultHandler {
	private final String PARENT_ELEMENT = "analysis";
	private final String FORM_ELEMENT = "form";



	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		InputStream is = new FileInputStream("c:\\greek.morph.xml");
		
		List<WordForm> list = new ArrayList<WordForm>();
		list.add(new WordForm("kai/"));
//		list.add(new WordForm("'fo/roun"));
//		list.add(new WordForm("'mme/nwn"));
//		list.add(new WordForm("'qa\\non"));
		
		
		GroupMatchHandler handler = new GroupMatchHandler(list);
		saxParser.parse(is, handler);

		for(int i=0; i<list.size();i++){
			WordForm element = list.get(i);
			System.out.println("Form: "+element.matchForm+" result: "+element.result.toString());
		}

	}

//	public static class WordForm{
//		public String matchForm; 
//		public Map<String,String> result;
//
//		public WordForm(String form){
//			System.out.println(form);
//			form = form.toLowerCase();
//			form = form.replace("\\", "/");
//			System.out.println(form);
//			this.matchForm = form;
//		}
//	}
	
	public static class WordForm{
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

//				System.out.println(matchForm);
				matchForm = matchForm.toLowerCase();
				matchForm = matchForm.replace("\\", "/");
//				System.out.println(matchForm);
			}catch(Exception e){

			}
		}
	}

	private List<WordForm> searchList = new ArrayList<WordForm>();
	public GroupMatchHandler(List<WordForm> searchList){
		this.searchList.addAll(searchList);
	}


	private boolean inFoundElement;
	private Map<String,String> elementFoundMap = new HashMap<String,String>();
	private StringBuffer tagText = new StringBuffer();

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
	}

	private List<WordForm> elementsFound = new ArrayList<WordForm>();

	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		String value = tagText.toString().trim();

		if(qName.equals(FORM_ELEMENT)){
//			System.out.println("InFormElement("+qName+"/"+value+")");
			for(int i=0; i < searchList.size(); i++){
				WordForm element = searchList.get(i);
				if(element.matchForm.equals(value)){
					inFoundElement  = true;
					elementsFound.add(element);
//					searchList.remove(elementFound);
//					System.out.println("elementFound: "+value);
				}
			}
		}
		
		if(inFoundElement && !qName.equals(PARENT_ELEMENT)){
			elementFoundMap.put(qName, value);
		}
		
		if(qName.equals(PARENT_ELEMENT)){
			if(inFoundElement){
//				System.out.println("FoundElement "+elementFoundMap.toString());
				for(WordForm element: elementsFound){
					element.result.add(elementFoundMap);
					element.lemma = elementFoundMap.get("form");
				}
				elementFoundMap = new HashMap<String,String>();
				elementsFound = new ArrayList<WordForm>();
			}
			inFoundElement = false;
		}

		tagText = new StringBuffer();

	}

	public void characters(char[] chars, int start, int length) throws SAXException {
		tagText.append(chars, start, length);
	}

}
