package controller;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dtos.WordMorphDTO;

public class HandlerMorph extends DefaultHandler{
	private String elementName = "analysis";
	private boolean inElement;

	private List<WordMorphDTO> result = new ArrayList<WordMorphDTO>();
	
	private WordMorphDTO aux;

	private StringBuffer tagText = new StringBuffer();

	public HandlerMorph(String elementName) {
		this.elementName = elementName;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//		System.out.println("StartElement: "+qName.toString());
		if (qName.equals(elementName)){
//			System.out.println(qName);
			aux = new WordMorphDTO();
			inElement = true;
		}
	}


	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		String text = tagText.toString().trim();
		//		System.out.println("qName: "+qName+"\t\telementName:"+elementName+"\t\tEndElement: "+text);
		//		System.out.println("EndElement: "+qName);
		if (qName.equals(elementName)) {
//			System.out.println(qName);
			result.add(aux);
			inElement = false;
			
		} else {
			if (inElement){
				/*
				x	analyses/analysis/case
				x	analyses/analysis/degree
				x	analyses/analysis/dialect
				x	analyses/analysis/feature
				x	analyses/analysis/form
				x	analyses/analysis/gender
				x	analyses/analysis/lemma
				x	analyses/analysis/mood
				x	analyses/analysis/number
				x	analyses/analysis/person
				x	analyses/analysis/pos
				x	analyses/analysis/tense
				x	analyses/analysis/voice
					*/
				
//				System.out.println(qName+" text "+text);

				switch(qName){
				case "case":
					aux.wCase = text;
					break;
				case "degree":
					aux.degree = text;
					break;
				case "dialect":
					aux.dialect = text;
					break;
				case "feature":
					aux.feature = text;
					break;
				case "form":
					aux.form = text;
					break;
				case "gender":
					aux.gender = text;
					break;
				case "lemma":
					aux.lemma = text;
					break;
				case "mood":
					aux.mode = text;
					break;
				case "number":
					aux.number  = text;
					break;
				case "person":
					aux.person = text;
					break;
				case "pos":
					aux.position = text;
					break;
				case "tense":
					aux.tense = text;
					break;
				case "voice":
					aux.voice = text;
					break;
				}
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
		return result;
	}
}
