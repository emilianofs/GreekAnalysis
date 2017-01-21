package utils;

public class MorphLoaderUtil {

//	public Word wordGet(List<Word> words, String word_betaCode){
//		Word response = null;
//		for(int i=0; i < words.size() && response == null; i++){
//			Word word = words.get(i);
//			if(word.getWord_betaCode().equals(word_betaCode)){
//				response = word;
//			}
//		}
//		return response;
//	}

//	public WordDialect wordDialectGetOrCreate(List<WordDialect> dialects, String dialect){
//		WordDialect response = null;
//		for(int i=0;i<dialects.size() && response == null;i++){
//			WordDialect aux = dialects.get(i);
//			if(aux == null)
//				System.out.println("aux null");
//			if(aux.getDialect() == null)
//				System.out.println("get dialect null");
//			if(aux.getDialect().equals(Dialect.fromString(dialect))){
//				response = aux;
//			}
//		}
//		if(response == null){
//			WordDialect newDialect = new WordDialect();
//			newDialect.setDialect(Dialect.fromString(dialect));
//			System.out.println("new dialect "+dialect);
//			System.out.println("new dialect "+dialect);
//			dialects.add(newDialect);
//		}
//		return response;
//	}
//	public WordLemma wordLemmaGet(Set<String> lemmasSet, List<WordLemma> lemmas, String lemma){
//		WordLemma response = null;
//		if(lemmasSet.contains(lemma)){
//			for(int i=0;i<lemmas.size() && response == null;i++){
//				WordLemma aux = lemmas.get(i);
//				if(aux.getWord_betaCode().equals(lemma)){
//					response = aux;
//				}
//			}
//		}
//		return response;
//	}
}
