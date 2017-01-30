package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entities.Author;
import entities.Discourse;
import entities.DiscourseFragment;

public class ControllerAuthor {
	private Set<Author> authors = new HashSet<Author>();
	private Set<Discourse> discourses = new HashSet<Discourse>();
	private Set<DiscourseFragment> fragments = new HashSet<DiscourseFragment>();

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
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

	public void heraclitoLoad () throws ParserConfigurationException{
//		List<DiscourseFragment> response = new ArrayList<DiscourseFragment>();
		File fXmlFile = new File("fragments.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("fragment");
		System.out.println("----------------------------");
		
		Author author = new Author();
		author.setName("Heraclito");

		Discourse discourse = new Discourse();
		discourse.setName("peri fusews");
		
		author.getDiscourses().add(discourse);
		
//		int id = 0;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
//			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				DiscourseFragment fragment = new DiscourseFragment();
				fragment.setNumber(eElement.getElementsByTagName("number").item(0).getTextContent());
				fragment.setText(eElement.getElementsByTagName("text").item(0).getTextContent().replace("\n", "").replace("\r", "").replace("\t", ""));
				fragments.add(fragment);
				discourse.getFragments().add(fragment);
//				System.out.println("Fragment No : " + eElement.getElementsByTagName("number").item(0).getTextContent());
//				System.out.println("Text : " + eElement.getElementsByTagName("text").item(0).getTextContent());

			}
		}
	}
	


}
