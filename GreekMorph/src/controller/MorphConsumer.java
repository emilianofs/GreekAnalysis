package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import dtos.WordMorphDTO;
import entities.words.Word;
import entities.words.WordDialect;
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
import utils.MorphLoaderUtil;

public class MorphConsumer implements Runnable {
	private static final Logger logger = Logger.getLogger( MorphConsumer.class.getName() );

	private BlockingQueue<WordMorphDTO> sharedQueueInput;
	private Set<String> sharedWords;
	private Set<String> sharedWordLemmas;
	//	private List<Word> sharedQueueWords;
	//	private List<WordLemma> sharedQueueLemmas;
	//	private MorphProducer producer;
	//	private ControllerLoader controllerLoader;
	private List<Word> words;
	private List<WordLemma> lemmas;
	private List<WordDialect> dialects;
	private List<String> strings = new ArrayList<String>();

	public MorphConsumer(BlockingQueue<WordMorphDTO> sharedQueue, Set<String> sharedStrings, Set <String> sharedWordLemmas, List<Word> words, List<WordLemma> lemmas, List<WordDialect> dialects){
		//		, List<WordLemma> lemmasList, List<Word> wordsList) {
		//	}
		this.sharedQueueInput = sharedQueue;
		this.words = words;
		this.lemmas = lemmas;		
		this.dialects = dialects;
		this.sharedWords = sharedStrings;
		this.sharedWordLemmas = sharedWordLemmas;
	}

	@Override
	public void run() {
		try {
			MorphLoaderUtil loaderUtils = new MorphLoaderUtil();
			while(sharedQueueInput.size() > 0){
				long startTime = System.nanoTime();
				WordMorphDTO element = sharedQueueInput.take();
				Word response = null;
				String form =  element.form;
				String lemma = element.lemma;

				//									System.out.println(Thread.currentThread().getName()+" left "+sharedQueueInput.size());
				//					if(true)
				//						return

				if(form != null && lemma != null){
					response = loaderUtils.wordGetOrCreate(sharedWords, words, form);
					if(response == null){
						if(form.equals(lemma)){
							response = new WordLemma();
							lemmas.add((WordLemma)response);
							//								this.lemmas.add((WordLemma) response);
						}else{
							response = new Word();
						}
						response.setWord_betaCode(form);
						words.add(response);
					}
				}else{
					System.out.println("[ERROR] Word does not have form OR lemma");
					//						return response;
				}		

				//					String position =  this.elementGet(element, "pos");
				//					String number = this.elementGet(element, "number");
				//					String tense = this.elementGet(element, "tense");
				//					String voice = this.elementGet(element, "voice");
				//					String mode = this.elementGet(element, "mood");
				//					String gender = this.elementGet(element, "gender");
				//					String person = this.elementGet(element, "person");
				//					String wCase= this.elementGet(element, "case");
				//					String dialects = this.elementGet(element, "dialect");
				//					String features = this.elementGet(element, "feature");

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
						declinedForm.setPosition(entities.words.WordForm.Position.fromString(position));
						declinedForm.setDeclension(Case.fromString(wCase));
						declinedForm.setGender(Gender.fromString(gender));
						declinedForm.setNumber(WordForm.Number.fromString(number));
						if(dialects != null){
							List<String> items = Arrays.asList(dialects.split("\\s*,\\s*"));
							for(int i=0;i<items.size();i++){
								String wordDialect = items.get(i);
								declinedForm.getDialects().add(loaderUtils.wordDialectGetOrCreate(this.dialects, wordDialect));
							}
						}
						if(features != null){
							List<String> items = Arrays.asList(features.split("\\s*,\\s*"));
							declinedForm.setFeatures(items);
						}
						response.getWordForms().add(declinedForm);
					}else{
						/**CASE NOT CONJUGATED AND NOT DECLINED*/
						WordForm wordForm = new WordForm();
						wordForm.setPosition(entities.words.WordForm.Position.fromString(position));
						if(dialects != null){
							List<String> items = Arrays.asList(dialects.split("\\s*,\\s*"));
							for(int i=0;i<items.size();i++){
								String wordDialect = items.get(i);
								wordForm.getDialects().add(loaderUtils.wordDialectGetOrCreate(this.dialects, wordDialect));
							}
						}
						if(features != null){
							List<String> items = Arrays.asList(features.split("\\s*,\\s*"));
							wordForm.setFeatures(items);
						}
						response.getWordForms().add(wordForm);

					}
				}else{
					/**CASE CONJUGATED**/
					if(wCase != null){
						/**CASE CONJUGATED AND DECLINED **/	
						WordFormConjugatedDeclined conjugatedDeclinedForm = new WordFormConjugatedDeclined();
						conjugatedDeclinedForm.setDeclension(Case.fromString(wCase));
						conjugatedDeclinedForm.setGender(Gender.fromString(gender));
						conjugatedDeclinedForm.setNumber(WordForm.Number.fromString(number));
						conjugatedDeclinedForm.setTense(Tense.fromString(tense));
						conjugatedDeclinedForm.setMode(Mode.fromString(mode));
						conjugatedDeclinedForm.setPerson(Person.fromString(person));
						conjugatedDeclinedForm.setVoice(Voice.fromString(voice));

						if(dialects != null){
							List<String> items = Arrays.asList(dialects.split("\\s*,\\s*"));
							for(int i=0;i<items.size();i++){
								String wordDialect = items.get(i);
								conjugatedDeclinedForm.getDialects().add(loaderUtils.wordDialectGetOrCreate(this.dialects, wordDialect));
							}
						}
						if(features != null){
							List<String> items = Arrays.asList(features.split("\\s*,\\s*"));
							conjugatedDeclinedForm.setFeatures(items);
						}
						response.getWordForms().add(conjugatedDeclinedForm);

					}else{
						/**CASE CONJUGATED AND NOT DECLINED**/
						WordFormConjugated conjugatedForm = new WordFormConjugated();
						conjugatedForm.setTense(Tense.fromString(tense));
						conjugatedForm.setMode(Mode.fromString(mode));
						conjugatedForm.setPerson(Person.fromString(person));
						conjugatedForm.setVoice(Voice.fromString(voice));
						if(dialects != null){
							List<String> items = Arrays.asList(dialects.split("\\s*,\\s*"));
							for(int i=0;i<items.size();i++){
								String wordDialect = items.get(i);
								conjugatedForm.getDialects().add(loaderUtils.wordDialectGetOrCreate(this.dialects, wordDialect));							}
						}
						if(features != null){
							List<String> items = Arrays.asList(features.split("\\s*,\\s*"));
							conjugatedForm.setFeatures(items);
						}
						response.getWordForms().add(conjugatedForm);

					}


				}


				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000000;  //divide by 1000000
				//				logger.info(Thread.currentThread().getName()+" RUNNING CONF TOOK "+duration+" msecs");

				//					return response;

			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}


}
