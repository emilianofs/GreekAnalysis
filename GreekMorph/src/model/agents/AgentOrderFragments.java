package model.agents;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import adts.GraphADT;
import adts.GraphLD;
import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordLemma;
import model.world.ModelWorld;

public class AgentOrderFragments extends Agent {
	private static final Logger logger = Logger.getLogger( AgentOrderFragments.class.getName() );

	private ModelWorld world;

	public AgentOrderFragments(ModelWorld world){
		this.world = world;
	}
	@Override
	public void run() throws Exception {
		if(world == null){
			throw new Exception();
		}

		Set<DiscourseFragment> fragments = world.getDiscourseFragments();
		Set<Word> words= world.getWords();
		Set<WordLemma> lemmas = world.getLemmas();
		
		GraphADT graph = new GraphLD();
		
//		for(DiscourseFragment d : fragments){
//			graph.addNode(d.getNumber());
//			}
		
		int i = 0;
		for(DiscourseFragment fragmentA: fragments){
			graph.addNode(fragmentA.getNumber());
			for(DiscourseFragment fragmentB: fragments){
				if(!graph.existsNode(fragmentB.getNumber())){
					graph.addNode(fragmentB.getNumber());
				}
				
				if(fragmentA != fragmentB && !graph.existsEdge(fragmentA.getNumber(), fragmentB.getNumber())
						&& !graph.existsEdge(fragmentB.getNumber(), fragmentA.getNumber())){
					String a = "FragA "+fragmentA.getNumber()+" FragB "+fragmentB.getNumber();
					Set<String> weight = this.weightFragments(fragmentA, fragmentB);
//					logger.info(a+": "+weight);
					if(weight.size() > 4){
						logger.info("Adding fragmentA "+fragmentA.getNumber()+" fragmentB "+fragmentB.getNumber()+" weight "+weight);
						graph.addEdge(fragmentA.getNumber(), fragmentB.getNumber(), weight);
//					if(weight > 3)
					}
				}
			}
		}
		graph.print();
//		
//		Set<GraphLD.Node> nodes = graph.nodes();
//		for(GraphLD.Node n: nodes){
//			logger.info("node "+n.node);
//		}
	}
	

	private Set<String> weightFragments(DiscourseFragment fragmentA, DiscourseFragment fragmentB) throws Exception{
		Set<String> response = new HashSet<String>();
		Set<String> fragA = fragmentA.getMorph().keySet();
		Set<String> fragB = fragmentB.getMorph().keySet();
		TransCoder tc = new TransCoder();
		tc.setParser("BetaCode");
		tc.setConverter("UnicodeC");
		
		for(String a: fragA){
			if(fragB.contains(a)){
//				logger.info("wordA "+a);
				response.add(tc.getString(a));
			}
		}
		return response;
	}
	
}
