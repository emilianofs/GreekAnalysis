package controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import dtos.WordMorphDTO;
import entities.words.Word;
import entities.words.WordDialect;
import entities.words.WordDialect.Dialect;
import entities.words.WordForm;
import entities.words.WordFormConjugated;
import entities.words.WordFormConjugated.Mode;
import entities.words.WordFormConjugated.Person;
import entities.words.WordFormConjugated.Tense;
import entities.words.WordFormConjugated.Voice;
import entities.words.WordFormConjugatedDeclined;
import entities.words.WordFormDeclined;
import entities.words.WordFormDeclined.Case;
import entities.words.WordFormDeclined.Gender;
import entities.words.WordLemma;

public class MorphConsumer implements Runnable {
	private static final Logger logger = Logger.getLogger( MorphConsumer.class.getName() );

	private BlockingQueue<WordMorphDTO> sharedQueueInput;
	private Map<String, Word> sharedWords;
	private Map<String, WordLemma> sharedWordLemmas;
	private Map<String, WordDialect> sharedWordDialects;
//	private Set<Word> words;
//	private Set<WordLemma> lemmas;
//	private Set<WordDialect> dialects;
//	private AtomicBoolean semaphore;

	public MorphConsumer(BlockingQueue<WordMorphDTO> sharedQueue, Map<String, Word> sharedWords, Map<String, WordLemma> sharedWordLemmas, Map<String, WordDialect> sharedWordDialects){
		//		, List<WordLemma> lemmasList, List<Word> wordsList) {
		//	}
		this.sharedQueueInput = sharedQueue;
		this.sharedWords = sharedWords;
		this.sharedWordLemmas = sharedWordLemmas;
		this.sharedWordDialects = sharedWordDialects;
	}

//	public WordDialect wordDialectGetOrCreate(Set<WordDialect> dialects, String dialect){
//		WordDialect response = null;
//		for(WordDialect aux: dialects){
//
//			if(aux == null)
//				System.out.println("aux null");
//			if(aux.getDialect() == null){
//				System.out.println("get dialect null "+dialect+" case "+Dialect.fromString(dialect));
//			}else{
//				if(aux.getDialect().equals(Dialect.fromString(dialect))){
//					response = aux;
//				}
//			}
//		}
//		if(response == null){
//			WordDialect newDialect = new WordDialect();
//			System.out.println("Adding new dialect "+dialect);
//			newDialect.setDialect(Dialect.fromString(dialect));
//			System.out.println("new dialect "+dialect);
//			dialects.add(newDialect);
//		}
//		return response;
//	}
	@Override
	public void run() {
		try {
			while(sharedQueueInput.size() > 0){
				//				logger.info(Thread.currentThread().getName()+"SIZE is "+sharedQueueInput.size()+" status "+semaphore.get());
				WordForm newForm;
				long startTime = System.nanoTime();
				WordMorphDTO element = sharedQueueInput.take();
				Word response = null;
				String form =  element.form;
				String lemma = element.lemma;

				if(form != null && lemma != null){
					if(sharedWords.get(form) != null){
						response = sharedWords.get(form);
					}
					if(response == null){
						if(form.equals(lemma)){
							WordLemma newLemma = new WordLemma();
							response = newLemma;
							//							lemmas.add((WordLemma)response);
							sharedWordLemmas.put(lemma, newLemma);
						}else{
							response = new Word();
							response.setWordForms(Collections.synchronizedList(response.getWordForms()));
							response.setWord_UTF8(form);
						}
						sharedWords.put(form, response);
						response.setWord_betaCode(form);
//						words.add(response);
					}
				}else{
					System.out.println("[ERROR] Word does not have form OR lemma");
					//						return response;
				}		

				String position =  element.position;
				String number = element.number;
				String tense = element.tense;
				String voice = element.voice;
				String mode = element.mode;
				String gender = element.gender;
				String person = element.person;
				String wCase= element.wCase;
				String dialects = element.dialect;
				String features = element.feature;


				if(tense == null){
					/**CASE NOT CONJUGATED**/
					if(wCase != null){
						/**CASE NOT CONJUGATED AND DECLINED**/
						WordFormDeclined declinedForm = new WordFormDeclined();
						newForm = declinedForm;
						declinedForm.setPosition(entities.words.WordForm.Position.fromString(position));
						declinedForm.setDeclension(Case.fromString(wCase));
						declinedForm.setGender(Gender.fromString(gender));
						declinedForm.setNumber(WordForm.Number.fromString(number));

						response.getWordForms().add(declinedForm);
					}else{
						/**CASE NOT CONJUGATED AND NOT DECLINED*/
						WordForm wordForm = new WordForm();
						newForm = wordForm;
						wordForm.setPosition(entities.words.WordForm.Position.fromString(position));

						response.getWordForms().add(wordForm);

					}
				}else{
					/**CASE CONJUGATED**/
					if(wCase != null){
						/**CASE CONJUGATED AND DECLINED **/	
						WordFormConjugatedDeclined conjugatedDeclinedForm = new WordFormConjugatedDeclined();
						newForm = conjugatedDeclinedForm;
						conjugatedDeclinedForm.setDeclension(Case.fromString(wCase));
						conjugatedDeclinedForm.setGender(Gender.fromString(gender));
						conjugatedDeclinedForm.setNumber(WordForm.Number.fromString(number));
						conjugatedDeclinedForm.setTense(Tense.fromString(tense));
						conjugatedDeclinedForm.setMode(Mode.fromString(mode));
						conjugatedDeclinedForm.setPerson(Person.fromString(person));
						conjugatedDeclinedForm.setVoice(Voice.fromString(voice));

						response.getWordForms().add(conjugatedDeclinedForm);

					}else{
						/**CASE CONJUGATED AND NOT DECLINED**/
						WordFormConjugated conjugatedForm = new WordFormConjugated();
						newForm = conjugatedForm;
						conjugatedForm.setTense(Tense.fromString(tense));
						conjugatedForm.setMode(Mode.fromString(mode));
						conjugatedForm.setPerson(Person.fromString(person));
						conjugatedForm.setVoice(Voice.fromString(voice));

						response.getWordForms().add(conjugatedForm);
					}
				}

				WordLemma lemmaForm = null;
				if(sharedWordLemmas.get(lemma) != null){
					lemmaForm = sharedWordLemmas.get(lemma);
				}
				if(lemmaForm == null){
					//					System.out.println("Creating lemma");
					lemmaForm = new WordLemma();
					lemmaForm.setWord_betaCode(lemma);
//					newForm.setLemma(lemmaForm);
					response.setLemma(lemmaForm);
					sharedWordLemmas.put(lemma, lemmaForm);
				}else{
					response.setLemma(lemmaForm);
//					newForm.setLemma(lemmaForm);
				}


				if(dialects != null){
					List<String> items = Arrays.asList(dialects.split("\\s+"));
					for(int i=0;i<items.size();i++){
						String wordDialect = items.get(i);
						WordDialect wordDialectO = this.sharedWordDialects.get(wordDialect);
						if(wordDialectO == null){
							wordDialectO = new WordDialect();
							wordDialectO.setDialect(Dialect.fromString(wordDialect));
							sharedWordDialects.put(wordDialect, wordDialectO);
						}else{
						newForm.getDialects().add(wordDialectO);
						}
					}
				}
				if(features != null){
					List<String> items = Arrays.asList(features.split("\\s+"));
					newForm.setFeatures(items);
				}




				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000;  //divide by 1000000
//				logger.info(Thread.currentThread().getName()+" RUNNING CONF TOOK "+duration+" micro secs");
				//				System.out.println("FINISH");
				//					return response;
			}
			logger.info(Thread.currentThread().getName()+" FINISHED");
			return;

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
