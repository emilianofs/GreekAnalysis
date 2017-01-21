package dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordMorphRawDTO {
	public List<Map<String,String>> result = new ArrayList<Map<String,String>>();
	public String lemmaString;
	public String word_betaCode;
	public String word_UTF8;
}
