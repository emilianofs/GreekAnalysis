package adts;

public interface GrafoTDA {
	void InicializarGrafo();
	void AgregarVertice(int vert);
	void AgregarArista(int vertO, int vertD, int peso);
	void EliminarVertice(int vert);
	void EliminarArista(int vertO, int vertD);
	ConjuntoTDA vertice();
	int PesoArista(int vertO, int vertD);
	boolean ExisteArista(int vertO, int vertD);
	ConjuntoTDA Adyacentes(int vert);
	int Elegir();
}
