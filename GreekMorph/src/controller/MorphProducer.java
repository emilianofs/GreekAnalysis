package controller;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

import dtos.WordMorphDTO;

public class MorphProducer implements Runnable {
	private BlockingQueue<WordMorphDTO> sharedQueueInput;
//	private List<Word> wordList;
//	private List<WordLemma> lemmaList;
//	private List<WordDialect> dialects;
	private Set<WordMorphDTO> words;

	
	public MorphProducer(BlockingQueue<WordMorphDTO> sharedQueue, Set<WordMorphDTO> words){
//	, BlockingQueue<WordLemma> lemmasQueue, BlockingQueue<Word> wordsQueue) {
		this.sharedQueueInput = sharedQueue;
		this.words = words;
//		this.wordList = words;
//		this.lemmaList = lemmas;
//		this.dialects = dialects;

//		this.sharedQueueLemmas = lemmasQueue;
//		this.sharedQueueWords = wordsQueue;
	}
	@Override
	public void run() {
		while(words.size() > 0){
			if(sharedQueueInput.size() < 500){
			WordMorphDTO dto = words.iterator().next();
				sharedQueueInput.add(dto);
				words.remove(dto);
			}
		}
		// TODO Auto-generated method stub
		
	}

}
