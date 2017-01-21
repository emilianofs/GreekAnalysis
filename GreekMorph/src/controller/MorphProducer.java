package controller;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import dtos.WordMorphDTO;

public class MorphProducer implements Runnable {
	private static final Logger logger = Logger.getLogger( MorphProducer.class.getName() );

	private BlockingQueue<WordMorphDTO> sharedQueueInput;
	private Set<WordMorphDTO> words;
	private AtomicBoolean semaphore;

	public MorphProducer(BlockingQueue<WordMorphDTO> sharedQueue, Set<WordMorphDTO> words, AtomicBoolean semaphore){
		this.sharedQueueInput = sharedQueue;
		this.words = words;
		this.semaphore = semaphore;
	}
	@Override
	public void run() {
		while(words.size() > 0){
			if(sharedQueueInput.size() < 500){
				WordMorphDTO dto = words.iterator().next();
				sharedQueueInput.add(dto);
				words.remove(dto);

			}else{
				try {
//					System.out.println("sleeping "+sharedQueueInput.size());
//					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		semaphore.set(false);
		logger.info(Thread.currentThread().getName()+" FINISHED");
		return;

	}

}
