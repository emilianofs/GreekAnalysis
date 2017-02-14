package model.agents;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import adts.GraphADT;
import adts.GraphLD;
import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import entities.words.Word;
import entities.words.WordLemma;
import model.world.ModelWorld;

public class AgentOrderFragmentsDistance extends Agent {
	private static final Logger logger = Logger.getLogger( AgentOrderFragmentsDistance.class.getName() );

	private ModelWorld world;

	public AgentOrderFragmentsDistance(ModelWorld world){
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

		TransCoder tc = new TransCoder();
		tc.setParser("BetaCode");
		tc.setConverter("UnicodeC");
		
		GraphADT<String, Float> graph = new GraphLD<String, Float>();
		
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
					Set<String> weight = this.weightFragments(fragmentA, fragmentB);
//					logger.info(a+": "+weight);
					if(weight.size() > 4){
						logger.info("Adding fragmentA "+fragmentA.getNumber()+" fragmentB "+fragmentB.getNumber()+" weight "+weight);
						graph.addEdge(fragmentA.getNumber(), fragmentB.getNumber(), Float.valueOf((1F / weight.size())));
//					if(weight > 3)
					}
				}
			}
		}
		
		
//		GraphLD<String, Float>.Node auxA = graph.first();
//		while(auxA != null){
//			GraphLD<String, Float>.Edge auxB = auxA.ady;
//			//			System.out.print("NodeA "+auxA.node+"\n");
//
//			while(auxB != null){
//				System.out.print(""+auxA.node+";"+auxB.nodeD.node+";"+ auxB.peso+";"+auxB.peso.toString()+"\n");
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
			pw = new PrintWriter(new File("/home/inwx/output/order2-"+dateFormat.format(date)+".csv"));
			StringBuilder sb = new StringBuilder();

			sb.append("NodeA");
			sb.append(',');
			sb.append("NodeB");
			sb.append(',');
			sb.append("Weight");
			sb.append('\n');

			pw.write(sb.toString());


			GraphLD<String, Float>.Node auxA = graph.first();
			

			while(auxA != null){
				GraphLD<String, Float>.Edge auxB = auxA.ady;
				//			System.out.print("NodeA "+auxA.node+"\n");

				while(auxB != null){
					sb = new StringBuilder();
					sb.append(tc.getString(auxA.node));
					sb.append(',');
					sb.append(tc.getString(auxB.nodeD.node));
					sb.append(',');
					sb.append(auxB.peso);
					sb.append('\n');
					pw.write(sb.toString());

					//					System.out.print(""+tc.getString(auxA.node)+";"+tc.getString(auxB.nodeD.node)+";"+ auxB.peso+"\n");
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
