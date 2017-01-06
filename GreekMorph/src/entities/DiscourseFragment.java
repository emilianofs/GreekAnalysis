package entities;
import java.util.ArrayList;
import java.util.List;

import entities.words.Word;

public class DiscourseFragment {
	public String number;
	public String text;
//	public List<WordForm> wordForm = new ArrayList<WordForm>();
	public List<Word> wordForm = new ArrayList<Word>();
	public List<Word> wordElements = new ArrayList<Word>();
}
