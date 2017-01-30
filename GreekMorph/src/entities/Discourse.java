package entities;

import java.util.HashSet;
import java.util.Set;

public class Discourse {
	private String name; 
	private Set<DiscourseFragment> fragments = new HashSet<DiscourseFragment>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<DiscourseFragment> getFragments() {
		return fragments;
	}
	public void setFragments(Set<DiscourseFragment> fragments) {
		this.fragments = fragments;
	}
	
	
}
