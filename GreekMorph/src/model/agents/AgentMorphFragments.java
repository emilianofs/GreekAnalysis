package model.agents;

import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordLemma;
import model.world.ModelWorld;

public class AgentMorphFragments extends Agent{
	private static final Logger logger = Logger.getLogger( AgentMorphFragments.class.getName() );

	private ModelWorld world;

	public AgentMorphFragments(ModelWorld world){
		this.world = world;
	}

	@Override
	public void run() throws Exception{
		if(world == null){
			throw new Exception();
		}
		TransCoder tc = new TransCoder();
		tc.setParser("BetaCode");
		tc.setConverter("UnicodeC");
		
		Set<DiscourseFragment> fragments = world.getDiscourseFragments();
		Set<Word> words= world.getWords();
		Set<WordLemma> lemmas = world.getLemmas();

		for(DiscourseFragment frag: fragments){
//			logger.info("Fragment "+frag.getNumber()+" Text "+frag.getText());
			for(String word: frag.getWordList()){
				if(this.includeWord(word)){
				Word morph = world.wordSearch(word);
				if(morph != null){
					frag.getMorph().put(morph.getLemma().getWord_betaCode(), morph);
				}else{
					logger.info("\t"+tc.getString(word)+" notfound");
				}
				}
			}
		}
		
		logger.info("FINAL---");
		for(DiscourseFragment frag: fragments){
			logger.info("Fragment ("+frag.getNumber()+"): "+frag.getText());
			
			Set<Entry<String, Word>> morph = frag.getMorph().entrySet();
			for(Entry<String, Word> entry: morph){
				logger.info("\tWord "+entry.getKey()+" forms:"+entry.getValue().getWordForms().size());	
			}
			
		}
	}
	
	private boolean includeWord(String word){
		boolean response = true;
		switch(word){
		case ")o":
			response = false;
			break;
		default:
			response = true;
			break;
		}
		
		return response;
	}
}
