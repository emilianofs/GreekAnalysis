package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dtos.WordMorphDTO;
import entities.words.Word;
import entities.words.WordDialect;
import entities.words.WordLemma;

public class ControllerLoader {


	private List<WordLemma> lemmas = new ArrayList<WordLemma>();
	private List<Word> words = new ArrayList<Word>();
	private List<WordDialect> dialects = new ArrayList<WordDialect>();

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public List<WordLemma> getLemmas() {
		return lemmas;
	}

	public void setLemmas(List<WordLemma> lemmas) {
		this.lemmas = lemmas;
	}

	/**************/
	public WordLemma lemmaGetOrCreate(String lemma){
		WordLemma response = null;

		for(int i=0; i<lemmas.size() && response == null; i++){
			WordLemma aux = lemmas.get(i);

			if (aux.lemma.equals(lemma)){
				response = aux;
			}
		}

		if(response == null){
			WordLemma newLemma = new WordLemma();
			newLemma.lemma = lemma;
			lemmas.add(newLemma);
			response = newLemma;

		}
		return response;

	}

	public void wordAdd(Word word){
		if(word != null){
			words.add(word);
		}
	}


	public void lemmasLoadSAX() throws Exception{
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		InputStream inputStream = new FileInputStream("/home/inwx/documents/GRIEGO/greek.morph.xml");
		ElementMatchHandler matchHandler = new ElementMatchHandler("analysis");
		saxParser.parse(inputStream, matchHandler);

		List<WordMorphDTO> results = matchHandler.getResults();
		System.out.println("Result "+results.size()+" words");

		//		for (int i = 0; i < results.size(); i++) {
		//			WordMorphDTO wordDto = results.get(i);
		//			Word newWord = this.wordCreate(wordDto);
		//			if(newWord != null){
		//				System.out.println(i);
		////				System.out.println("Word "+newWord.getWord_betaCode()+" with "+newWord.getWordForms().size()+" forms created");
		//			}
		//			
		//		}

//		BlockingQueue<WordMorphDTO> sharedQueueInput = new LinkedBlockingQueue<WordMorphDTO>();
//		Set<String> sharedWords = Collections.synchronizedSet(new HashSet<String>());
//		Set<WordMorphDTO> wordsDto = Collections.synchronizedSet(new HashSet<WordMorphDTO>(results));
//		List<Word> wordListSync = Collections.synchronizedList(words);
//		List<WordLemma> wordLemmaListSync = Collections.synchronizedList(lemmas);
//		List<WordDialect> wordDialectsListSync = Collections.synchronizedList(dialects);
//		ExecutorService executor = Executors.newFixedThreadPool(8);
//		System.out.println("DTOS "+wordsDto.size()+" items");
//
//        Runnable producer = new MorphProducer(sharedQueueInput, wordsDto);
//    	executor.execute(producer); 
//
//		for(int i = 0; i < 9; i++){
//			Runnable consumer = new MorphConsumer(sharedQueueInput, sharedWords, sharedWords, wordListSync, wordLemmaListSync, wordDialectsListSync);
//			executor.execute(consumer); 	
//		}
//		executor.shutdown();
//
//		while(!executor.isTerminated()){
//			System.out.println("Left "+sharedQueueInput.size());
//			System.out.println("Queue size "+sharedQueueInput.size()+" items");
//			System.out.println("Words "+words.size()+" forms created");
//			System.out.println("Word Lemmas "+lemmas.size()+" lemmas created");
//			Thread.sleep(5000);
//		}
	}
	public void lemmasLoadDom() throws Exception{
		System.out.println("Starting lemmasLoad()----------------------------");

		File fXmlFile = new File("/home/inwx/documents/GRIEGO/greek.morph.xml");
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
		NodeList nList = doc.getElementsByTagName("analysis");
		System.out.println("\tStarting to parse");



		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			//			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				//				Word newWord = this.wordCreate(eElement);
				//				if(newWord != null){
				//					System.out.println("Word "+newWord.getWord_betaCode()+" with "+newWord.getWordForms().size()+" forms created");
				//				}

			}
		}
		System.out.println("Created "+words.size()+" words");

	}
	public Word wordGetOrCreate(String word_betaCode){
		Word response = null;

		for(int i=0; i < words.size() && response == null; i++){
			Word word = words.get(i);
			if(word.getWord_betaCode().equals(word_betaCode)){
				response = word;
			}
		}

		return response;
	}
}
