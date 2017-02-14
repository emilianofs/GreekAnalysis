package model.agents;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import adts.GraphADT;
import adts.GraphLD;
import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordFormConjugated;
import entities.words.WordLemma;
import model.world.ModelWorld;

public class AgentOrderLemmas extends Agent {
	private static final Logger logger = Logger.getLogger( AgentOrderLemmas.class.getName() );

	private ModelWorld world;

	public AgentOrderLemmas(ModelWorld world){
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


		GraphADT<String, Set<String>> graph = new GraphLD<String, Set<String>>();
		//		Set<String> nodes = new HashSet<String>();

		for(DiscourseFragment fragmentA: fragments){
			for(String lemma: fragmentA.getMorph().keySet()){
				if(!graph.existsNode(lemma)){
					graph.addNode(lemma);
				}
			}
		}


		TransCoder tc = new TransCoder();
		tc.setParser("BetaCode");
		tc.setConverter("UnicodeC");

		for(DiscourseFragment fragmentA: fragments){
//			graph.addNode(fragmentA.getNumber());
			for(String lemma: fragmentA.getMorph().keySet()){
//				if(fragmentA.getMorph().get(lemma) instanceof WordFormConjugated){
				String nodeA = tc.getString(lemma);
				for(String lemmaB: fragmentA.getMorph().keySet()){
					String nodeB = tc.getString(lemmaB);
					
					if(!nodeA.equals(nodeB)){
						if(!graph.existsNode(nodeA)){
							graph.addNode(nodeA);
						}
						
						if(!graph.existsNode(nodeB)){
							graph.addNode(nodeB);
						}
						
						if(!graph.existsEdge(nodeA, nodeB) && !graph.existsEdge(nodeB, nodeA)){
							Set<String> edgeLemmas = new HashSet<String>();
							edgeLemmas.add(fragmentA.getNumber());
							graph.addEdge(nodeA, nodeB, edgeLemmas);
						}else{
						
							Float weight = null;
							try{
							Set<String> edgeLemmas = graph.weight(nodeA, nodeB);
							edgeLemmas.add(fragmentA.getNumber());
//							graph.removeEdge(nodeA, nodeB);
//							graph.addEdge(nodeA, nodeB, weight);
							}catch(Exception e){
							
								if(weight == null){
									Set<String> edgeLemmas = graph.weight(nodeB, nodeA);
//									weight = 1 / (weight + 1);
									edgeLemmas.add(fragmentA.getNumber());
//									graph.removeEdge(nodeB, nodeA);
//									graph.addEdge(nodeB, nodeA, weight);
								}
							}
						
						}
					}

				}


				//				if(!graph.existsNode(lemma)){
				//					graph.addNode(lemma);
				//				}
				//				
				//				if(!graph.existsEdge(lemma, fragmentA.getNumber()) 
				//						|| !graph.existsEdge(fragmentA.getNumber(), lemma)){
				//					graph.addEdge(lemma, fragmentA.getNumber(), 1);
				//				}else{
				//					Integer weight = graph.weight(lemma, fragmentA.getNumber());
				//					if(weight == null){
				//						weight = graph.weight(fragmentA.getNumber(), lemma);
				//					}
				//					weight = weight + 1;
				//				}
			}
		}

		//		GraphLD<String, Integer>.Node auxA = graph.first();
		//		while(auxA != null){
		//			GraphLD<String, Integer>.Edge auxB = auxA.ady;
		//			//			System.out.print("NodeA "+auxA.node+"\n");
		//
		//			while(auxB != null){
		//				System.out.print(""+tc.getString(auxA.node)+";"+tc.getString(auxB.nodeD.node)+";"+ auxB.peso+"\n");
		//				auxB = auxB.sig;
		//
		//			}
		//
		//			auxA = auxA.sig;
		//		}


		PrintWriter pw = null;
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

		try {
			pw = new PrintWriter(new File("/home/inwx/output/lemma-"+dateFormat.format(date)+".csv"));
			StringBuilder sb = new StringBuilder();

			sb.append("NodeA");
			sb.append(';');
			sb.append("NodeB");
			sb.append(';');
			sb.append("Weight");
			sb.append(";");
			sb.append("Lemmas");
			sb.append('\n');

			pw.write(sb.toString());


			GraphLD<String, Set<String>>.Node auxA = graph.first();
			

			while(auxA != null){
				GraphLD<String, Set<String>>.Edge auxB = auxA.ady;
				//			System.out.print("NodeA "+auxA.node+"\n");

				while(auxB != null){
					if(auxB.peso.size() > 1){
					sb = new StringBuilder();
					sb.append(tc.getString(auxA.node));
					sb.append(';');
					sb.append(tc.getString(auxB.nodeD.node));
					sb.append(';');
					sb.append(Float.valueOf(1F/auxB.peso.size()));
					sb.append(';');
					sb.append(auxB.peso.toString());
					sb.append('\n');
					pw.write(sb.toString());

					//					System.out.print(""+tc.getString(auxA.node)+";"+tc.getString(auxB.nodeD.node)+";"+ auxB.peso+"\n");
					}
					auxB = auxB.sig;

				}

				auxA = auxA.sig;
			}

			pw.close();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}

	}


	//	private Set<String> weightFragments(DiscourseFragment fragmentA, DiscourseFragment fragmentB) throws Exception{
	//		Set<String> response = new HashSet<String>();
	//		Set<String> fragA = fragmentA.getMorph().keySet();
	//		Set<String> fragB = fragmentB.getMorph().keySet();
	//		TransCoder tc = new TransCoder();
	//		tc.setParser("BetaCode");
	//		tc.setConverter("UnicodeC");
	//		
	//		for(String a: fragA){
	//			if(fragB.contains(a)){
	////				logger.info("wordA "+a);
	//				response.add(tc.getString(a));
	//			}
	//		}
	//		return response;
	//	}

}
