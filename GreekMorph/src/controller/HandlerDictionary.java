package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dtos.WordMorphDTO;
import entities.words.Word;

public class HandlerDictionary extends DefaultHandler{
	private String elementName = "entryFree";
	private boolean inElement;

	private Map<String, Word> mapWords = new HashMap<String, Word>();
	
	private StringBuffer tagText = new StringBuffer();

	public HandlerDictionary(String elementName) {
		this.elementName = elementName;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals(elementName)){
//			System.out.println(qName);
//			aux = new WordMorphDTO();
			inElement = true;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		String text = tagText.toString().trim();
		if (qName.equals(elementName)) {
//			result.add(aux);
			inElement = false;
			
		} else {
			if (inElement){
				
				
//				System.out.println(qName+" text "+text);

//				switch(qName){
//				case "case":
//					aux.wCase = text;
//					break;
//				case "degree":
//					aux.degree = text;
//					break;
//				case "dialect":
//					aux.dialect = text;
//					break;
//				case "feature":
//					aux.feature = text;
//					break;
//				case "form":
//					aux.form = text;
//					break;
//				case "gender":
//					aux.gender = text;
//					break;
//				case "lemma":
//					aux.lemma = text;
//					break;
//				case "mood":
//					aux.mode = text;
//					break;
//				case "number":
//					aux.number  = text;
//					break;
//				case "person":
//					aux.person = text;
//					break;
//				case "pos":
//					aux.position = text;
//					break;
//				case "tense":
//					aux.tense = text;
//					break;
//				case "voice":
//					aux.voice = text;
//					break;
//				}
			}

		}
		tagText = new StringBuffer();
	}

	public void characters(char[] chars, int start, int length) throws SAXException {
		if (inElement){
			tagText.append(chars, start, length);
			//			System.out.println("Characters: "+tagText.toString());
		}
	}

	public List<WordMorphDTO> getResults(){
		return null;
	}
}
