package dataModels;

public class ConjuntoLD implements ConjuntoTDA {

	class Nodo{
		int valor;
		Nodo sig;	
	}
	Nodo valores;
	
	@Override
	public void Agregar(int elem) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ConjuntoVacio() {
		return(valores == null);
	}

	@Override
	public void Sacar(int elem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void InicializarConjunto() {
		valores = new Nodo();
	}

	@Override
	public int Elegir() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean Pertenece(int elem) {
		// TODO Auto-generated method stub
		return false;
	}

}
