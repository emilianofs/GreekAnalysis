package dataModels;


public class GrafoLD implements GrafoTDA {

	class NodoVertice{
		int vertice;
		NodoArista ady;
		NodoVertice sig;
	}
	
	class NodoArista{
		int peso;
		NodoVertice vertD;
		NodoArista sig;
	}
	
	NodoVertice vertices;
	
	@Override
	public ConjuntoTDA Adyacentes(int vert) {
		NodoVertice Origen = buscarVertice(vert);
		ConjuntoTDA adyacentes = new ConjuntoLD();
		adyacentes.InicializarConjunto();
		
		NodoArista auxArista = Origen.ady;
		
		while(auxArista != null){
			adyacentes.Agregar(auxArista.vertD.vertice);
			auxArista = auxArista.sig;
		}
		return adyacentes;
	}

	@Override
	public void AgregarArista(int vertO, int vertD, int peso) {
		NodoVertice Origen = buscarVertice(vertO);
		NodoVertice Destino = buscarVertice(vertD);
		
		if(Origen != null && Destino != null){
			NodoArista auxArista = Origen.ady;
			while(auxArista != null && auxArista.vertD != Destino){
				auxArista = auxArista.sig;
			}
			
			if(auxArista != null){
				auxArista.peso = peso;
			}else{
				NodoArista nueva = new NodoArista();
				nueva.peso = peso;
				nueva.vertD = Destino;
				nueva.sig = Origen.ady;
				Origen.ady = nueva;
			}
		}
	}
	
	@Override
	public void AgregarVertice(int vert) {
		NodoVertice aux = vertices;
		
		while(aux != null && aux.vertice != vert){
			aux = aux.sig;
		}
	
		if(aux == null){
			NodoVertice nuevo = new NodoVertice();
			nuevo.vertice = vert;
			nuevo.sig = vertices;
			vertices = nuevo;
		}
	}

	@Override
	public int Elegir() {
		return vertices.vertice;
	}

	@Override
	public void EliminarArista(int vertO, int vertD) {
		NodoVertice Origen = buscarVertice(vertO);
		NodoVertice Destino = buscarVertice(vertD);
		
		if(Origen != null && Destino != null){
			if(Origen.ady.vertD == Destino){
				Origen.ady = Origen.ady.sig;
			}else{
				NodoArista auxArista = Origen.ady;
				while(auxArista.sig != null && auxArista.sig.vertD != Destino){
					auxArista = auxArista.sig;
				}	
				if(auxArista != null){
					auxArista.sig = auxArista.sig.sig;
				}
			}
		}
	}

	@Override
	public void EliminarVertice(int vert) {
		NodoVertice vertElim = buscarVertice(vert);
		if(vertElim != null){ // Si el vertice existe...
			NodoVertice auxVert = vertices;
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
			NodoVertice auxVert = vertices;
			while(auxVert.sig != null && auxVert.sig != vertElim){
				auxVert = auxVert.sig;
			}
			if(auxVert.sig != null){
				auxVert.sig = auxVert.sig.sig;
			}
		}	
	}

	@Override
	public boolean ExisteArista(int vertO, int vertD) {
		NodoVertice Origen = buscarVertice(vertO);
		NodoVertice Destino = buscarVertice(vertD);
		if(Origen == null){
			return false;
		}else{
			NodoArista aristas = Origen.ady;
			while(aristas != null && aristas.vertD != Destino){
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
	public void InicializarGrafo() {
		vertices = null;
	}

	@Override
	public int PesoArista(int vertO, int vertD) {
		NodoVertice Origen = buscarVertice(vertO);
		NodoVertice Destino = buscarVertice(vertD);
		NodoArista aristas = Origen.ady;
		while(aristas.vertD != Destino){
			aristas = aristas.sig;
		}
		return aristas.peso;
	}

	@Override
	public ConjuntoTDA vertice() {
		ConjuntoTDA vert = new ConjuntoLD();
		vert.InicializarConjunto();
		NodoVertice auxVert = vertices;
		while(auxVert != null){
			vert.Agregar(auxVert.vertice);
			auxVert = auxVert.sig;
		}
		return vert;
	}

	private NodoVertice buscarVertice(int vert){
		NodoVertice aux = vertices;
		while(aux != null && aux.vertice != vert){
			aux = aux.sig;
		}
		return aux;		
	}
	private void EliminarValorLista(NodoVertice vertice, NodoVertice v){
		if(vertice.ady.vertD == v){
			vertice.ady = vertice.ady.sig;
		}else{
			NodoArista aux = vertice.ady;
			while(aux.sig != null && aux.sig.vertD == v){
				aux = aux.sig;
			}
			if(aux.sig != null){
				aux.sig = aux.sig.sig;
			}
		}
		
	}
}
