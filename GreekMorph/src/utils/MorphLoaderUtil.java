package utils;

import java.util.List;
import java.util.Set;

import entities.words.Word;
import entities.words.WordDialect;
import entities.words.WordDialect.Dialect;

public class MorphLoaderUtil {

	public Word wordGetOrCreate(Set<String> wordsSet, List<Word> words, String word_betaCode){
		Word response = null;

		if(words.contains(word_betaCode)){
			for(int i=0; i < words.size() && response == null; i++){
				Word word = words.get(i);
				if(word.getWord_betaCode().equals(word_betaCode)){
					response = word;
					wordsSet.add(word_betaCode);
				}
			}
		}

		return response;
	}
	
	public WordDialect wordDialectGetOrCreate(List<WordDialect> dialects, String dialect){
		WordDialect response = null;
		for(int i=0;i<dialects.size() && response == null;i++){
			WordDialect aux = dialects.get(i);
			if(aux.equals(dialect)){
				response = aux;
			}
		}
		if(response == null){
			WordDialect newDialect = new WordDialect();
			newDialect.setDialect(Dialect.fromString(dialect));
			dialects.add(newDialect);
		}
		return response;
	}
}
