package adts;

public interface ConjuntoTDA {
	void Agregar(int elem);
	boolean ConjuntoVacio();
	void InicializarConjunto();
	int Elegir();
	boolean Pertenece(int elem);
	void Sacar(int elem);
}
