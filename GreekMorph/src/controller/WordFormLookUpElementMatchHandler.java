package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WordFormLookUpElementMatchHandler extends DefaultHandler {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		//		InputStream is = ElementMatchHandler.class.getResourceAsStream("c:\\greek.morph.xml");
		InputStream is = new FileInputStream("c:\\greek.morph.xml");
		WordFormLookUpElementMatchHandler matchHandler = new WordFormLookUpElementMatchHandler("analysis", "form", "'*enuw/");
		saxParser.parse(is, matchHandler);
		
		Map found = matchHandler.getFound();
		
		if (found != null)
			System.out.println("matchHandler.getFound() = " + found);
		else
			System.out.println("Not found!");
	}

	private String elementName;
	private String matchElement;
	private String matchValue;
	private Map found = new HashMap();

	private boolean inElement;
	private boolean isMatched;
	private Map elementMap = new HashMap();
	private StringBuffer tagText = new StringBuffer();

	public WordFormLookUpElementMatchHandler(String elementName, String matchElement, String matchValue) {
		this.elementName = elementName;
		this.matchElement = matchElement;
		this.matchValue = matchValue;
		System.out.println("elementName: "+elementName+"\tmatchElement: "+matchElement+"\tmatchValue: "+matchValue);
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//				System.out.println("StartElement: "+qName.toString());
		if (qName.equals(elementName)){
			inElement = true;
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		String text = tagText.toString().trim();
		//		System.out.println("qName: "+qName+"\t\telementName:"+elementName+"\t\tEndElement: "+text);
		//		System.out.println("EndElement: "+qName);
		if (qName.equals(elementName)) {
			if (isMatched){
//				found = elementMap;
				found.putAll(elementMap);
				elementMap = new HashMap();
			}
			isMatched = false;
			inElement = false;
		} else {
			if (inElement)
				elementMap.put(qName, text);
			if (qName.equals(matchElement) && text.equals(matchValue)){
				System.out.println("qName: "+qName+"\t\tmatchElement:"+matchElement+"\t\tmatchValue: "+matchValue+"\t\ttext: "+text);
				isMatched = true;
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

	public Map getFound() {
		return found;
	}
}
