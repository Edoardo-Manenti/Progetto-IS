package it.unibs.inge.IS.ProgettoIS;

import java.util.Objects;

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
	@Override
	public String toString() {
		return origin.toString() + "->" + destination.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Arco arco = (Arco) o;
		return Objects.equals(origin, arco.origin) && Objects.equals(destination, arco.destination);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origin, destination);
	}
}
