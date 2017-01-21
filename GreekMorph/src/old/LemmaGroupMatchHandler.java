package old;
//package controller;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//import org.xml.sax.helpers.DefaultHandler;
//
//import dtos.WordMorphDTO;
//import dtos.WordMorphRawDTO;
//import entities.words.Word;
//import entities.words.WordLemma;
//
//public class LemmaGroupMatchHandler extends DefaultHandler {
//	private final String PARENT_ELEMENT = "analysis";
//	private final String FORM_ELEMENT = "form";
//
//	private Controller controller;
//
//	public Controller getController() {
//		return controller;
//	}
//
//	public void setController(Controller controller) {
//		this.controller = controller;
//	}
//	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
//		InputStream is = new FileInputStream("/home/inwx/documents/GRIEGO/greek.morph.xml");
//		
//		List<Word> list = new ArrayList<Word>();
//		list.add(new Word("kai/"));
////		list.add(new Word("'fo/roun"));
////		list.add(new Word("'mme/nwn"));
////		list.add(new Word("'qa\\non"));
//		
//		
//		LemmaGroupMatchHandler handler = new LemmaGroupMatchHandler();
//		saxParser.parse(is, handler);
//
//		for(int i=0; i<list.size();i++){
//			Word element = list.get(i);
//			System.out.println("Form: "+element.getWord_UTF8()+" result: "+element.result.toString());
//		}
//
//	}
//
////	public static class Word{
////		public String matchForm; 
////		public Map<String,String> result;
////
////		public Word(String form){
////			System.out.println(form);
////			form = form.toLowerCase();
////			form = form.replace("\\", "/");
////			System.out.println(form);
////			this.matchForm = form;
////		}
////	}
//	
//
//
//	private List<Word> searchList = new ArrayList<Word>();
////	public LemmaGroupMatchHandler(List<Word> searchList){
////		this.searchList.addAll(searchList);
////	}
////
//
//	private boolean inFoundElement;
//	private Map<String,String> elementFoundMap = new HashMap<String,String>();
//	private StringBuffer tagText = new StringBuffer();
//
//	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		
//	}
//
//	private List<WordMorphRawDTO> elementsFound = new ArrayList<WordMorphRawDTO>();
//
//	public void endElement(String uri, String localName, String qName) throws SAXException {
//		super.endElement(uri, localName, qName);
//		String value = tagText.toString().trim();
//
//		if(qName.equals(FORM_ELEMENT)){
////			System.out.println("InFormElement("+qName+"/"+value+")");
//			for(int i=0; i < searchList.size(); i++){
//				Word element = searchList.get(i);
//				if(element.getWord_UTF8().equals(value)){
//					inFoundElement  = true;
//					elementsFound.add(element);
////					searchList.remove(elementFound);
////					System.out.println("elementFound: "+value);
//				}
//			}
//		}
//		
//		if(inFoundElement && !qName.equals(PARENT_ELEMENT)){
//			elementFoundMap.put(qName, value);
//		}
//		
//		if(qName.equals(PARENT_ELEMENT)){
//			if(inFoundElement){
////				System.out.println("FoundElement "+elementFoundMap.toString());
//				for(Word element: elementsFound){
//					element.result.add(elementFoundMap);
//					element.lemmaString = elementFoundMap.get("form");
//				}
//				elementFoundMap = new HashMap<String,String>();
//				elementsFound = new ArrayList<Word>();
//			}
//			inFoundElement = false;
//		}
//
//		tagText = new StringBuffer();
//
//	}
//
//	public WordLemma searchWordLemma(String lemma){
//		WordLemma response = null;
//		
//		
//		return response;
//	}
//	public void characters(char[] chars, int start, int length) throws SAXException {
//		tagText.append(chars, start, length);
//	}
//
//	public List<WordMorphDTO> getResults(){
//		return elementsFound;
//	}
//}
//
//public class GroupMatchHandler extends DefaultHandler {
//	private final String PARENT_ELEMENT = "analysis";
//	private final String FORM_ELEMENT = "form";
//
//	private Controller controller;
//
//	public Controller getController() {
//		return controller;
//	}
//
//	public void setController(Controller controller) {
//		this.controller = controller;
//	}
//	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
//		InputStream is = new FileInputStream("/home/inwx/documents/GRIEGO/greek.morph.xml");
//		
//		List<Word> list = new ArrayList<Word>();
//		list.add(new Word("kai/"));
////		list.add(new Word("'fo/roun"));
////		list.add(new Word("'mme/nwn"));
////		list.add(new Word("'qa\\non"));
//		
//		
//		LemmaGroupMatchHandler handler = new LemmaGroupMatchHandler(list);
//		saxParser.parse(is, handler);
//
//		for(int i=0; i<list.size();i++){
//			Word element = list.get(i);
//			System.out.println("Form: "+element.getWord_UTF8()+" result: "+element.result.toString());
//		}
//
//	}
//
////	public static class Word{
////		public String matchForm; 
////		public Map<String,String> result;
////
////		public Word(String form){
////			System.out.println(form);
////			form = form.toLowerCase();
////			form = form.replace("\\", "/");
////			System.out.println(form);
////			this.matchForm = form;
////		}
////	}
//	
//
//
//	private List<Word> searchList = new ArrayList<Word>();
//	public GroupMatchHandler(List<Word> searchList){
//		this.searchList.addAll(searchList);
//	}
//
//
//	private boolean inFoundElement;
//	private Map<String,String> elementFoundMap = new HashMap<String,String>();
//	private StringBuffer tagText = new StringBuffer();
//
//	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		
//	}
//
//	private List<Word> elementsFound = new ArrayList<Word>();
//
//	public void endElement(String uri, String localName, String qName) throws SAXException {
//		super.endElement(uri, localName, qName);
//		String value = tagText.toString().trim();
//
//		if(qName.equals(FORM_ELEMENT)){
////			System.out.println("InFormElement("+qName+"/"+value+")");
//			for(int i=0; i < searchList.size(); i++){
//				Word element = searchList.get(i);
//				if(element.getWord_UTF8().equals(value)){
//					inFoundElement  = true;
//					elementsFound.add(element);
////					searchList.remove(elementFound);
////					System.out.println("elementFound: "+value);
//				}
//			}
//		}
//		
//		if(inFoundElement && !qName.equals(PARENT_ELEMENT)){
//			elementFoundMap.put(qName, value);
//		}
//		
//		if(qName.equals(PARENT_ELEMENT)){
//			if(inFoundElement){
////				System.out.println("FoundElement "+elementFoundMap.toString());
//				for(Word element: elementsFound){
//					element.result.add(elementFoundMap);
//					element.lemmaString = elementFoundMap.get("form");
//				}
//				elementFoundMap = new HashMap<String,String>();
//				elementsFound = new ArrayList<Word>();
//			}
//			inFoundElement = false;
//		}
//
//		tagText = new StringBuffer();
//
//	}
//
//	public WordLemma searchWordLemma(String lemma){
//		WordLemma response = null;
//		
//		
//		return response;
//	}
//	public void characters(char[] chars, int start, int length) throws SAXException {
//		tagText.append(chars, start, length);
//	}
//
//}
