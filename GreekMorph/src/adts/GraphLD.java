package adts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphLD implements GraphADT {

	public class Node{
		public String node;
		public Edge ady;
		public Node sig;
	}

	public class Edge{
		//		public int peso;
		Set<String> peso = new HashSet<String>();
		public Node nodeD;
		public Edge sig;
	}

	Node vertices;

	@Override
	public Set<Node> adjacents(String node) {
		Node Origen = buscarVertice(node);
		Set<Node> adyacentes = new HashSet<Node>();
		//		adyacentes.InicializarConjunto();

		Edge auxArista = Origen.ady;

		while(auxArista != null){
			adyacentes.add(auxArista.nodeD);
			auxArista = auxArista.sig;
		}
		return adyacentes;
	}

	@Override
	public void addEdge(String nodeA, String nodeB, Set<String> weight) {
		Node Origen = buscarVertice(nodeA);
		Node Destino = buscarVertice(nodeB);

		if(Origen != null && Destino != null){
			Edge auxArista = Origen.ady;
			while(auxArista != null && auxArista.nodeD != Destino){
				auxArista = auxArista.sig;
			}

			if(auxArista != null){
				auxArista.peso = (Set<String>) weight;
			}else{
				Edge nueva = new Edge();
				nueva.peso = weight;
				nueva.nodeD = Destino;
				nueva.sig = Origen.ady;
				Origen.ady = nueva;
			}
		}
	}

	@Override
	public void addNode(String node) {
		Node aux = vertices;

		while(aux != null && !aux.node.equals(node)){
			aux = aux.sig;
		}

		if(aux == null){
			Node nuevo = new Node();
			nuevo.node = node;
			nuevo.sig = vertices;
			vertices = nuevo;
		}
	}

	@Override
	public String choose() {
		return vertices.node;
	}

	@Override
	public void removeEdge(String nodeA, String nodeB) {
		Node Origen = buscarVertice(nodeA);
		Node Destino = buscarVertice(nodeB);

		if(Origen != null && Destino != null){
			if(Origen.ady.nodeD == Destino){
				Origen.ady = Origen.ady.sig;
			}else{
				Edge auxArista = Origen.ady;
				while(auxArista.sig != null && auxArista.sig.nodeD != Destino){
					auxArista = auxArista.sig;
				}	
				if(auxArista != null){
					auxArista.sig = auxArista.sig.sig;
				}
			}
		}
	}

	@Override
	public void removeNode(String node) {
		Node vertElim = buscarVertice(node);
		if(vertElim != null){ // Si el vertice existe...
			Node auxVert = vertices;
			while(auxVert != null){
				if(auxVert != vertElim && auxVert.ady != null){
					//Elimina de los demas vertices toda referencia a vertElim
					this.EliminarValorLista(auxVert, vertElim);
				}
				auxVert = auxVert.sig;
			}
		}

		if(vertices == vertElim){
			vertices = vertices.sig;	
		}else{
			Node auxVert = vertices;
			while(auxVert.sig != null && auxVert.sig != vertElim){
				auxVert = auxVert.sig;
			}
			if(auxVert.sig != null){
				auxVert.sig = auxVert.sig.sig;
			}
		}	
	}

	@Override
	public boolean existsEdge(String nodeA, String nodeB) {
		Node Origen = buscarVertice(nodeA);
		Node Destino = buscarVertice(nodeB);
		if(Origen == null){
			return false;
		}else{
			Edge aristas = Origen.ady;
			while(aristas != null && aristas.nodeD != Destino){
				aristas = aristas.sig;
			}
			if(aristas == null){
				return false;
			}else{
				return true;
			}
		}
	}

	@Override
	public void init() {
		vertices = null;
	}

	@Override
	public Set<String> weight(String nodeA, String nodeB) {
		Node Origen = buscarVertice(nodeA);
		Node Destino = buscarVertice(nodeB);
		Edge aristas = Origen.ady;
		while(aristas.nodeD != Destino){
			aristas = aristas.sig;
		}
		return aristas.peso;
	}

	@Override
	public Set<Node> nodes() {
		Set<Node> vert = new HashSet<Node>();
		//		vert.InicializarConjunto();
		Node auxVert = vertices;
		while(auxVert != null){
			vert.add(auxVert);
			auxVert = auxVert.sig;
		}
		return vert;
	}

	private Node buscarVertice(String node){
		Node aux = vertices;
		while(aux != null && !aux.node.equals(node)){
			aux = aux.sig;
		}
		return aux;		
	}
	private void EliminarValorLista(Node vertice, Node v){
		if(vertice.ady.nodeD == v){
			vertice.ady = vertice.ady.sig;
		}else{
			Edge aux = vertice.ady;
			while(aux.sig != null && aux.sig.nodeD == v){
				aux = aux.sig;
			}
			if(aux.sig != null){
				aux.sig = aux.sig.sig;
			}
		}

	}

	@Override
	public boolean existsNode(String nodeA) {
		boolean response = false;
		Node aux = vertices;
		while(aux != null && !aux.node.equals(nodeA)){
			aux = aux.sig;
		}
		if(aux != null){
			response = true;
		}
		return response;	
	}

	@Override
	public void print(){
		Node auxA = vertices;
		while(auxA != null){
			Edge auxB = auxA.ady;
			//			System.out.print("NodeA "+auxA.node+"\n");

			while(auxB != null){
				System.out.print(""+auxA.node+";"+auxB.nodeD.node+";"+auxB.peso.size()+";"+auxB.peso.toString()+"\n");
				auxB = auxB.sig;

			}

			auxA = auxA.sig;
		}
	}
}
