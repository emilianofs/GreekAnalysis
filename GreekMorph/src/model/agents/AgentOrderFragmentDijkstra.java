package model.agents;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import adts.GraphADT;
import adts.GraphLD;
import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordLemma;
import model.world.ModelWorld;

public class AgentOrderFragmentDijkstra extends Agent {
	//	Imprime Fragment, Lemma - No Graph
	private static final Logger logger = Logger.getLogger( AgentOrderFragmentDijkstra.class.getName() );

	private ModelWorld world;

	public AgentOrderFragmentDijkstra(ModelWorld world){
		this.world = world;
	}
	@Override
	public void run() throws Exception {
		if(world == null){
			throw new Exception();
		}

		TransCoder tc = new TransCoder();
		tc.setParser("BetaCode");
		tc.setConverter("UnicodeC");

		Set<DiscourseFragment> fragments = world.getDiscourseFragments();
		Set<Word> words= world.getWords();
		Set<WordLemma> lemmas = world.getLemmas();


		GraphADT<String, Set<String>> graph = new GraphLD<String, Set<String>>();


		for(DiscourseFragment fragmentA: fragments){
			graph.addNode(fragmentA.getNumber());
			for(DiscourseFragment fragmentB: fragments){
				if(!graph.existsNode(fragmentB.getNumber())){
					graph.addNode(fragmentB.getNumber());
				}
				Set<String> weight = this.weightFragments(fragmentA, fragmentB);
				if(weight.size() >0){
					//				&& 
					if(fragmentA != fragmentB && !graph.existsEdge(fragmentA.getNumber(), fragmentB.getNumber())){
						weight = this.weightFragments(fragmentA, fragmentB);
						logger.info("Adding fragmentA "+fragmentA.getNumber()+" fragmentB "+fragmentB.getNumber()+" weight "+weight);
						graph.addEdge(fragmentA.getNumber(), fragmentB.getNumber(), weight);

					}

					if(fragmentA != fragmentB && !graph.existsEdge(fragmentB.getNumber(), fragmentA.getNumber())){
						weight = this.weightFragments(fragmentA, fragmentB);
						logger.info("Adding fragmentB "+fragmentA.getNumber()+" fragmentA "+fragmentB.getNumber()+" weight "+weight);
						graph.addEdge(fragmentB.getNumber(), fragmentA.getNumber(), weight);

					}
				}
			}
		}

		PrintWriter pw = null;
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

		try {
			//			pw = new PrintWriter(new File("C:\\output\\lemma-"+dateFormat.format(date)+".csv"));
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
					//					if(auxB.peso.size() > 1){
					sb = new StringBuilder();
					sb.append(tc.getString(auxA.node));
					sb.append(';');
					sb.append(tc.getString(auxB.nodeD.node));
					sb.append(';');
					//					sb.append(Float.valueOf(1F/auxB.peso.size()));
					sb.append(Float.valueOf(auxB.peso.size()));
					sb.append(';');
					sb.append(auxB.peso.toString());
					sb.append('\n');
					pw.write(sb.toString());

					//					System.out.print(""+tc.getString(auxA.node)+";"+tc.getString(auxB.nodeD.node)+";"+ auxB.peso+"\n");
					//					}
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
