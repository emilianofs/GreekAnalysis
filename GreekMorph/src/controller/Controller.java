package controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import controller.GroupMatchHandler.WordForm;
import edu.unc.epidoc.transcoder.TransCoder;

public class Controller extends DefaultHandler  {

	public static void main(String[] args) {


		//		String source = "A)/NDRA MOI E)/NNEPE, MOU=SA";
		//		String source = "A)/NDRA";
		try {
			//			String source = "A)/NDRA MOI E)/NNEPE, MOU=SA";
			//			TransCoder tc = new TransCoder();
			//			tc.setParser("BetaCode");
			//			tc.setConverter("UnicodeD");
			//			String result = tc.getString(source);


			//				String source = "τιμῶσι";
//			String source = "ἀρηιφάτους θεοὶ τιμῶσι καὶ ἄνθρωποι";
//			String source = "εἰ πάντα τὰ ὄντα καπνὸς γένοιτο ῥῖνες ἂν διαγνοῖεν";
			String source = "ὄνους σύρματ' ἂν ἑλέσθαι μᾶλλον ἢ χρυσόν";
//			TransCoder tc = new TransCoder();
//			tc.setParser("Unicode");
//			tc.setConverter("BetaCode");
//			String result = tc.getString(source);
//
//			System.out.println(source);
//			System.out.println(result);
			
			
			String[] words = source.split(" ");

			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			InputStream is = new FileInputStream("c:\\greek.morph.xml");

			List<WordForm> list = new ArrayList<WordForm>();
			for(String word: words){
				list.add(new WordForm(word));
			}

			GroupMatchHandler handler = new GroupMatchHandler(list);

			saxParser.parse(is, handler);

			for(int i=0; i <list.size(); i++){
				WordForm element = list.get(i);
				if(element.result != null){
					System.out.println("OriginalForm: "+element.originalForm+" result: "+element.result.size());
				}
			}
//aaaa

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
