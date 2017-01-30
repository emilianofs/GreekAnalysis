package adts;

import java.util.Set;

import adts.GraphLD.Node;

public interface GraphADT {
	void init();
	void addNode(String node);
	void addEdge(String nodeA, String nodeB, Set<String> weight);
	void removeNode(String node);
	void removeEdge(String nodeA, String nodeB);
	Set<Node> nodes();
	Set<String> weight(String nodeA, String nodeB);
	boolean existsEdge(String nodeA, String nodeB);
	boolean existsNode(String nodeA);
	Set<Node> adjacents(String node);
	String choose();
	void print();
}
