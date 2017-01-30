package controller;

import java.util.logging.Logger;

import org.xml.sax.helpers.DefaultHandler;

import entities.words.Language;
import model.agents.AgentMorphFragments;
import model.world.ModelWorld;


public class Controller extends DefaultHandler  {
	private static final Logger logger = Logger.getLogger( Controller.class.getName() );

	private static ControllerMorph lemmaController = new ControllerMorph();
	private static ControllerAuthor authorController = new ControllerAuthor();
	
	public static void main(String[] args){
			System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
//		Controller controller = new Controller();
		try {
//			System.out.println("loadLemmas()");
//			controller.mainViejo(args);
			authorController.heraclitoLoad();
			lemmaController.morphLoadSAX("/home/inwx/documents/GRIEGO/greek.morph.xml", Language.LanguageName.GREEK);
//			lemmaController.morphLoadSAX("/home/inwx/documents/GRIEGO/latin.morph.xml", Language.LanguageName.LATIN);
			
			logger.info("Languages Loaded "+lemmaController.getLanguages().size());
			logger.info("Words Loaded "+lemmaController.getWords().size());
			logger.info("Lemmas Loaded "+lemmaController.getLemmas().size());
			
			ModelWorld world = new ModelWorld(lemmaController.getWords(), lemmaController.getLemmas(), authorController.getFragments());
			world.run();
			
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
//	public void mainViejo(String[] args) {
//		Controller controller = new Controller();
//		try {
//	
////			String source = "ὄνους σύρματ' ἂν ἑλέσθαι μᾶλλον ἢ χρυσόν";
//			//			TransCoder tc = new TransCoder();
//			//			tc.setParser("Unicode");
//			//			tc.setConverter("BetaCode");
//			//			String result = tc.getString(source);
//			//
//			//			System.out.println(source);
//			//			System.out.println(result);
////			String[] words = source.split(" ");
//
//			List<Word> list = new ArrayList<Word>();
//			
//			List<DiscourseFragment> fragments = controller.readXml();
//			for(DiscourseFragment f: fragments){
//				System.out.println("Number: "+f.number+" Text: "+f.text);
//				String[] words = f.text.split(" ");
//				for(String word: words){
//					word = word.replace(".", "");
//					word = word.replace("·", "");
//					word = word.replace(",", "");
//					f.wordForm.add(new Word(word));
//				}
//				list.addAll(f.wordForm);				
//			}
//			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
//			InputStream is = new FileInputStream("/home/inwx/documents/GRIEGO/greek.morph.xml");
//
//			LemmaGroupMatchHandler handler = new LemmaGroupMatchHandler(list);
//
//			System.out.println("START PARSE----------------------------");
//			System.out.println("Looking up for "+list.size()+" words");
//			saxParser.parse(is, handler);
//
//			System.out.println("END PARSE----------------------------\n");
//				
//			List<WordLemma> words= new ArrayList<WordLemma>();
//			
//			TransCoder tc = new TransCoder();
//			tc.setParser("BetaCode");
//			tc.setConverter("UnicodeC");
//			
//			// Lemma -> Formas
//			for(int i=0; i <fragments.size(); i++){
//				DiscourseFragment fragment = fragments.get(i);
//				System.out.println("Number: "+fragment.number+" Text: "+fragment.text);
//				for(int j=0; j<fragment.wordForm.size();j++){
//					Word element = fragment.wordForm.get(j);
////					System.out.println("\t\tOriginalForm: "+element.originalForm+" matchForm: '"+element.matchForm+"' result: "+element.result.size());
////					System.out.println("LEMA "+element.lemma);
//					
//					
//					
//					for(int k=0; k<element.result.size();k++){
//						String word = element.getWord_betaCode();
//						String lemma = element.result.get(k).get("lemma");
//						lemma = tc.getString(lemma);
//						
//						WordLemma wordLemma = controller.searchLemma(words, lemma);
//						Word wordElement = new Word();
//						
//						boolean exists = true;
//						for(int l=0; l < wordLemma.elements.size(); l++){
//							Word aux = wordLemma.elements.get(l);
//							if(aux.getWord_betaCode().equals(word) && aux.fragment.number.equals(fragment.number)){
//								exists = false;
//							}
//						}
//						if(exists){
//							wordElement.fragment = fragment;
//							wordElement.setWord_betaCode(word);
//							wordLemma.elements.add(wordElement);
//						}
//
//					}
//				}
//			}
//			
////			Graph graph = new SingleGraph("Tutorial 1");
//
//			
//			System.out.println("----------------------------");
//			int i = 0;
//			for(DiscourseFragment f: fragments){
//				System.out.println(i+++";"+f.number);
////				graph.addNode(f.number);
//			}
//			
//			System.out.println("----------------------------");
//			
////			Set<String> keySet = map.keySet();
////			for(String key: keySet){
////				System.out.println(key+ ";"+map.get(key).size());
////				List<String> forms = map.get(key);
////				for(int i=0; i<forms.size(); i++){
////					System.out.println("Form: "+forms.get(i));
////				}
////			}
////			Graph graph = new Graph(fragments.size());
////			
////			for(Word word: words){
//////				System.out.print(word.lemma+";"+word.elements.size()+";");
////				for(WordElement wordElement1: word.elements){
////					
////					Fragment fragment1 = wordElement1.fragment;
////					for(WordElement wordElement2: word.elements){
////						Fragment fragment2 = wordElement2.fragment;
////						if(!graph.isEdge(fragment1.id, fragment2.id)){
////							graph.addEdge(fragment1.id, fragment2.id);
////						}
////					}
////
////				}
////			}
////			
////			graph.print();
//			/************************************************/
////			for(Word word: words){
////				for(WordElement wordElement1: word.elements){
////					Fragment fragment1 = wordElement1.fragment;
////					for(WordElement wordElement2: word.elements){
////						Fragment fragment2 = wordElement2.fragment;
////						try{
////							graph.addEdge(fragment1.number+"-"+fragment2.number, fragment1.number, fragment2.number);
////						}catch(Exception e){
////							e.printStackTrace();
////						}
////					}
////
////				}
////			}
////			graph.display();
//			/************************************************/
////			for(Word word: words){
////				System.out.print(word.lemma+";"+word.elements.size()+";");
////				for(WordElement wordElement: word.elements){
////					System.out.print(wordElement.fragment.number+",");
////				}
////				System.out.println("");
////			}
//			
//					
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
//	public WordLemma searchLemma(List<WordLemma> words, String lemma){
//		WordLemma response = null;
//		for(int i=0; i < words.size(); i++){
//			if(words.get(i).lemma.equals(lemma)){
//				response = words.get(i);
//			}
//		}
//		
//		if(response == null){
//			WordLemma newLemma = new WordLemma();
//			newLemma.lemma = lemma;
//			newLemma.elements = new ArrayList<Word>();
//			words.add(newLemma);
//			response = newLemma;
//		}
//		
//		
//		return response;
//	}
	
}
