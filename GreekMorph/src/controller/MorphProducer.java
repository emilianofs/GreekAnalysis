package controller;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

import dtos.WordMorphDTO;

public class MorphProducer implements Runnable {
	private BlockingQueue<WordMorphDTO> sharedQueueInput;
	private Set<WordMorphDTO> words;

	
	public MorphProducer(BlockingQueue<WordMorphDTO> sharedQueue, Set<WordMorphDTO> words){
		this.sharedQueueInput = sharedQueue;
		this.words = words;
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
