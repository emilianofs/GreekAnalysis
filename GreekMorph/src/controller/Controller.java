package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordLemma;


public class Controller extends DefaultHandler  {

	public static void main(String[] args) {
		Controller controller = new Controller();
		try {
	
//			String source = "ὄνους σύρματ' ἂν ἑλέσθαι μᾶλλον ἢ χρυσόν";
			//			TransCoder tc = new TransCoder();
			//			tc.setParser("Unicode");
			//			tc.setConverter("BetaCode");
			//			String result = tc.getString(source);
			//
			//			System.out.println(source);
			//			System.out.println(result);
//			String[] words = source.split(" ");

			List<Word> list = new ArrayList<Word>();
			
			List<DiscourseFragment> fragments = controller.readXml();
			for(DiscourseFragment f: fragments){
				System.out.println("Number: "+f.number+" Text: "+f.text);
				String[] words = f.text.split(" ");
				for(String word: words){
					word = word.replace(".", "");
					word = word.replace("·", "");
					word = word.replace(",", "");
					f.wordForm.add(new Word(word));
				}
				list.addAll(f.wordForm);				
			}
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			InputStream is = new FileInputStream("c:\\greek.morph.xml");

			GroupMatchHandler handler = new GroupMatchHandler(list);

			System.out.println("START PARSE----------------------------");
			System.out.println("Looking up for "+list.size()+" words");
			saxParser.parse(is, handler);

			System.out.println("END PARSE----------------------------\n");
				
			List<WordLemma> words= new ArrayList<WordLemma>();
			
			TransCoder tc = new TransCoder();
			tc.setParser("BetaCode");
			tc.setConverter("UnicodeC");
			
			// Lemma -> Formas
			for(int i=0; i <fragments.size(); i++){
				DiscourseFragment fragment = fragments.get(i);
				System.out.println("Number: "+fragment.number+" Text: "+fragment.text);
				for(int j=0; j<fragment.wordForm.size();j++){
					Word element = fragment.wordForm.get(j);
//					System.out.println("\t\tOriginalForm: "+element.originalForm+" matchForm: '"+element.matchForm+"' result: "+element.result.size());
//					System.out.println("LEMA "+element.lemma);
					
					
					
					for(int k=0; k<element.result.size();k++){
						String word = element.originalForm;
						String lemma = element.result.get(k).get("lemma");
						lemma = tc.getString(lemma);
						
						WordLemma wordLemma = controller.searchLemma(words, lemma);
						Word wordElement = new Word();
						
						boolean exists = true;
						for(int l=0; l < wordLemma.elements.size(); l++){
							Word aux = wordLemma.elements.get(l);
							if(aux.originalForm.equals(word) && aux.fragment.number.equals(fragment.number)){
								exists = false;
							}
						}
						if(exists){
							wordElement.fragment = fragment;
							wordElement.originalForm = word;
							wordLemma.elements.add(wordElement);
						}

					}
				}
			}
			
//			Graph graph = new SingleGraph("Tutorial 1");

			
			System.out.println("----------------------------");
			int i = 0;
			for(DiscourseFragment f: fragments){
				System.out.println(i+++";"+f.number);
//				graph.addNode(f.number);
			}
			
			System.out.println("----------------------------");
			
//			Set<String> keySet = map.keySet();
//			for(String key: keySet){
//				System.out.println(key+ ";"+map.get(key).size());
//				List<String> forms = map.get(key);
//				for(int i=0; i<forms.size(); i++){
//					System.out.println("Form: "+forms.get(i));
//				}
//			}
//			Graph graph = new Graph(fragments.size());
//			
//			for(Word word: words){
////				System.out.print(word.lemma+";"+word.elements.size()+";");
//				for(WordElement wordElement1: word.elements){
//					
//					Fragment fragment1 = wordElement1.fragment;
//					for(WordElement wordElement2: word.elements){
//						Fragment fragment2 = wordElement2.fragment;
//						if(!graph.isEdge(fragment1.id, fragment2.id)){
//							graph.addEdge(fragment1.id, fragment2.id);
//						}
//					}
//
//				}
//			}
//			
//			graph.print();
			/************************************************/
//			for(Word word: words){
//				for(WordElement wordElement1: word.elements){
//					Fragment fragment1 = wordElement1.fragment;
//					for(WordElement wordElement2: word.elements){
//						Fragment fragment2 = wordElement2.fragment;
//						try{
//							graph.addEdge(fragment1.number+"-"+fragment2.number, fragment1.number, fragment2.number);
//						}catch(Exception e){
//							e.printStackTrace();
//						}
//					}
//
//				}
//			}
//			graph.display();
			/************************************************/
//			for(Word word: words){
//				System.out.print(word.lemma+";"+word.elements.size()+";");
//				for(WordElement wordElement: word.elements){
//					System.out.print(wordElement.fragment.number+",");
//				}
//				System.out.println("");
//			}
			
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	public List<DiscourseFragment> readXml () throws ParserConfigurationException{
		List<DiscourseFragment> response = new ArrayList<DiscourseFragment>();
		File fXmlFile = new File("fragments.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("fragment");
		System.out.println("----------------------------");
		int id = 0;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
//			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				DiscourseFragment fragment = new DiscourseFragment();
				fragment.number =  eElement.getElementsByTagName("number").item(0).getTextContent();
				fragment.text =  eElement.getElementsByTagName("text").item(0).getTextContent().replace("\n", "").replace("\r", "").replace("\t", "");
				response.add(fragment);
//				System.out.println("Fragment No : " + eElement.getElementsByTagName("number").item(0).getTextContent());
//				System.out.println("Text : " + eElement.getElementsByTagName("text").item(0).getTextContent());

			}
		}
		return response;
	}
	
	public WordLemma searchLemma(List<WordLemma> words, String lemma){
		WordLemma response = null;
		for(int i=0; i < words.size(); i++){
			if(words.get(i).lemma.equals(lemma)){
				response = words.get(i);
			}
		}
		
		if(response == null){
			WordLemma newLemma = new WordLemma();
			newLemma.lemma = lemma;
			newLemma.elements = new ArrayList<Word>();
			words.add(newLemma);
			response = newLemma;
		}
		
		
		return response;
	}
	
}
