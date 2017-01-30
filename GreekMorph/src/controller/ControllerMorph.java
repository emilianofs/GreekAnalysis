package controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import dtos.WordMorphDTO;
import entities.words.Language;
import entities.words.Word;
import entities.words.WordDialect;
import entities.words.WordDialect.Dialect;
import entities.words.WordLemma;

public class ControllerMorph {
	private static final Logger logger = Logger.getLogger( ControllerMorph.class.getName() );


	private Set<WordLemma> lemmas = new HashSet<WordLemma>();
	private Set<Word> words = new HashSet<Word>();
	private Set<WordDialect> dialects = new HashSet<WordDialect>();
	private Set<Language> languages = new HashSet<Language>();


	public Set<WordLemma> getLemmas() {
		return lemmas;
	}


	public void setLemmas(Set<WordLemma> lemmas) {
		this.lemmas = lemmas;
	}


	public Set<Word> getWords() {
		return words;
	}


	public void setWords(Set<Word> words) {
		this.words = words;
	}


	public Set<WordDialect> getDialects() {
		return dialects;
	}


	public void setDialects(Set<WordDialect> dialects) {
		this.dialects = dialects;
	}


	public Set<Language> getLanguages() {
		return languages;
	}


	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}


	/**************/

	public void morphLoadSAX(String file, Language.LanguageName language) throws Exception{
		Language lang = new Language();
		lang.setName(language);
		languages.add(lang);
		
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		InputStream inputStream = new FileInputStream(file);
		HandlerMorph matchHandler = new HandlerMorph("analysis");
		saxParser.parse(inputStream, matchHandler);

		List<WordMorphDTO> results = matchHandler.getResults();
		System.out.println("Result "+results.size()+" words");

		BlockingQueue<WordMorphDTO> sharedQueueInput = new LinkedBlockingQueue<WordMorphDTO>(results);
		AtomicBoolean semaphore = new AtomicBoolean(true);
		Map<String, Word> sharedWords = Collections.synchronizedMap(new HashMap<String, Word>());
		Map<String, WordLemma> sharedWordLemmas = Collections.synchronizedMap(new HashMap<String, WordLemma>());
		Map<String, WordDialect> sharedWordDialects = Collections.synchronizedMap(new HashMap<String, WordDialect>());

		List<String> dialects = new ArrayList<String>();
		dialects.add("attic");
		dialects.add("epic");
		dialects.add("doric");
		dialects.add("ionic");
		dialects.add("aeolic");
		dialects.add("parad_form");
		dialects.add("prose");
		dialects.add("poetic");
		dialects.add("homeric");

		for(int i=0;i<dialects.size();i++){
			String wordDialect = dialects.get(i);
			WordDialect dialect = new WordDialect();
			dialect.setDialect(Dialect.fromString(wordDialect));
			sharedWordDialects.put(wordDialect, dialect);							
		}
		logger.info("Dialects preloaded - "+sharedWordDialects.size());

		//		for(WordMorphDTO aux: results){
		//			if(!sharedWordLemmas.contains(aux.lemma)){
		//				sharedWordLemmas.add(aux.lemma);
		//				WordLemma auxLemma = new WordLemma();
		//				auxLemma.setWord_betaCode(aux.lemma);
		//				wordLemmaListSync.add(auxLemma);
		//			}
		//			
		//		}
		//		logger.info("Lemmas preloaded - "+wordLemmaListSync.size()+" words "+sharedWordLemmas.size());

		ExecutorService executor = Executors.newFixedThreadPool(2);
		//		Runnable producer = new MorphProducer(sharedQueueInput, wordsDto, semaphore);
		//		executor.execute(producer); 


		for(int i = 0; i < 2; i++){
			Runnable consumer = new MorphConsumer(sharedQueueInput, sharedWords, sharedWordLemmas, sharedWordDialects);
			executor.execute(consumer); 	
		}

		executor.shutdown();
		//		try {
		//			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}

		while(!executor.isTerminated()){
			//	System.out.println("Left "+sharedQueueInput.size());
			System.out.println("Queue size "+sharedQueueInput.size()+" items "+semaphore.get());
			System.out.println("Words "+sharedWords.size()+" forms created");
			System.out.println("Word Lemmas "+sharedWordLemmas.size()+" lemmas created");
			Thread.sleep(1000);
		}


		System.out.println("FIN Queue size "+sharedQueueInput.size()+" items");
		System.out.println("FIN Words "+sharedWords.size()+" forms created");
		System.out.println("FIN Word Lemmas "+sharedWordLemmas.size()+" lemmas created");
		
		for(Entry<String, Word> entry: sharedWords.entrySet()){
			Word thisWord = entry.getValue();
			thisWord.setLanguage(lang);
			lang.getWords().add(thisWord);
			this.words.add(thisWord);
		}

		for(Entry<String, WordLemma> entry: sharedWordLemmas.entrySet()){
			WordLemma thisLemma = entry.getValue();
			lang.getLemmas().add(thisLemma);
			this.lemmas.add(thisLemma);
		}


		for(Entry<String, WordDialect> entry: sharedWordDialects.entrySet()){
			WordDialect thisDialect = entry.getValue();
			lang.getDialects().add(thisDialect);
			this.dialects.add(thisDialect);
		}

	}

	public void dictionaryLoad(String file, Language.LanguageName lang) throws Exception{
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		InputStream inputStream = new FileInputStream(file);
		HandlerDictionary matchHandler = new HandlerDictionary("analysis");
		saxParser.parse(inputStream, matchHandler);
		
		
	}
	
}
