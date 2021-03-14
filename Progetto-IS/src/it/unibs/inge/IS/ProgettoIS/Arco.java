package it.unibs.inge.IS.ProgettoIS;

public class Arco {
	private Nodo origin;
	private Nodo destination;
	
	public Arco(Nodo o, Nodo d) {
		this.origin = o;
		this.destination = d;
	}
	
	public Nodo getOrigin() {
		return this.origin;
	}
	
	public Nodo getDestination() {
		return this.destination;
	}
}
