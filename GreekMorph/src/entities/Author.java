package entities;

import java.util.HashSet;
import java.util.Set;

public class Author {
	private String name;
	private Set<Discourse> discourses = new HashSet<Discourse>();
	private Set<DiscourseFragment> fragments = new HashSet<DiscourseFragment>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Discourse> getDiscourses() {
		return discourses;
	}
	public void setDiscourses(Set<Discourse> discourses) {
		this.discourses = discourses;
	}
	public Set<DiscourseFragment> getFragments() {
		return fragments;
	}
	public void setFragments(Set<DiscourseFragment> fragments) {
		this.fragments = fragments;
	}
	
}
