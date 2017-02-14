package model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordLemma;
import model.agents.Agent;
import model.agents.AgentMorphFragments;
import model.agents.AgentOrderFragments;
import model.agents.AgentOrderFragmentsDistance;
import model.agents.AgentOrderLemmaCounter;
import model.agents.AgentOrderLemmas;

public class ModelWorld {
	private static final Logger logger = Logger.getLogger( ModelWorld.class.getName() );

	private Set<WordLemma> lemmas;
	private Set<Word> words;
	private Set<DiscourseFragment> discourseFragments;
	private Map<String, Word> wordsMap = new HashMap<String, Word>();
	private List<Agent> agents = new ArrayList<Agent>();

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


	public Set<DiscourseFragment> getDiscourseFragments() {
		return discourseFragments;
	}


	public void setDiscourseFragments(Set<DiscourseFragment> discourseFragments) {
		this.discourseFragments = discourseFragments;
	}

	public List<Agent> getAgents() {
		return agents;
	}


	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}


	public ModelWorld(Set<Word> words, Set<WordLemma> lemmas, Set<DiscourseFragment> discourseFragments) throws Exception{
		this.lemmas = lemmas;
		this.words = words;
		this.discourseFragments = discourseFragments;
		AgentMorphFragments agent = new AgentMorphFragments(this);
		agents.add(agent);
//		AgentOrderFragments agent2 = new AgentOrderFragments(this);
//		agents.add(agent2);
		AgentOrderLemmas agent3 = new AgentOrderLemmas(this);
		agents.add(agent3);
//		AgentOrderFragmentsDistance agent4 = new AgentOrderFragmentsDistance(this);
//		agents.add(agent4);
//		AgentOrderLemmaCounter agent5 = new AgentOrderLemmaCounter(this);
//		agents.add(agent5);
//		TransCoder tc = new TransCoder();
//		tc.setParser("BetaCode");
//		tc.setConverter("UnicodeB");
//		String result = tc.getString(source);
		
		for(Word w: words){
//			String utf8 = tc.getString(w.getWord_betaCode());
//			logger.info("word "+utf8);
			this.wordsMap.put(w.getWord_betaCode(), w);
		}
				
	}
	/***************/

	public Word wordSearch(String word){
		Word response = null;
//		logger.info("Looking for word "+word);
		if(this.wordsMap.containsKey(word)){
			response = this.wordsMap.get(word);
//			logger.info("returning word "+response.getWord_betaCode());
		}
		return response;
	}
	
	public void run() throws Exception{
		for(Agent agent: agents){
			agent.run();
		}
	}
}
