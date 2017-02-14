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

import adts.GraphLD;
import edu.unc.epidoc.transcoder.TransCoder;
import entities.DiscourseFragment;
import model.world.ModelWorld;

public class AgentOrderLemmaCounter extends Agent {
	private static final Logger logger = Logger.getLogger( AgentOrderLemmaCounter.class.getName() );

	private ModelWorld world;

	public AgentOrderLemmaCounter(ModelWorld world){
		this.world = world;
	}
	@Override
	public void run() throws Exception {
		if(world == null){
			throw new Exception();
		}

		Set<DiscourseFragment> fragments = world.getDiscourseFragments();
//		Set<Word> words= world.getWords();
//		Set<WordLemma> lemmas = world.getLemmas();


		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		TransCoder tc = new TransCoder();
		tc.setParser("BetaCode");
		tc.setConverter("UnicodeC");

		for(DiscourseFragment fragmentA: fragments){
//			graph.addNode(fragmentA.getNumber());
			for(String lemma: fragmentA.getMorph().keySet()){
				String lemmaU = tc.getString(lemma);
				
				Set<String> lemmas = null;
				if(map.containsKey(fragmentA.getNumber())){
					lemmas = map.get(fragmentA.getNumber());
				}else{
					lemmas = new HashSet<String>();
					map.put(fragmentA.getNumber(), lemmas);
				}
				lemmas.add(lemmaU);
			}
		}

		PrintWriter pw = null;
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

		try {
			pw = new PrintWriter(new File("/home/inwx/output/frag-"+dateFormat.format(date)+".csv"));
			StringBuilder sb = new StringBuilder();

			sb.append("Fragment");
			sb.append(',');
			sb.append("Lemma");
			sb.append('\n');

			pw.write(sb.toString());

			for(String frag : map.keySet()){
				for(String lemma: map.get(frag)){
					sb = new StringBuilder();
					sb.append(frag);
					sb.append(',');
					sb.append(lemma);
					sb.append('\n');
					pw.write(sb.toString());

				}
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
