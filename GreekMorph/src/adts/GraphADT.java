package adts;

import java.util.Set;

import adts.GraphLD.Node;

public interface GraphADT<N,W> {
	void init();
	void addNode(N node);
	void addEdge(N nodeA, N nodeB, W weight);
	void removeNode(N node);
	void removeEdge(N nodeA, N nodeB);
	Set<GraphLD<N,W>.Node> nodes();
	W weight(N nodeA, N nodeB);
	boolean existsEdge(N nodeA, N nodeB);
	boolean existsNode(N nodeA);
	Set<GraphLD<N,W>.Node> adjacents(N node);
	GraphLD<N,W>.Node buscarVertice(N node);
	GraphLD<N,W>.Node first();
	N choose();
}
