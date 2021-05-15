package model;


import java.util.Objects;

public class Arco {
	private String id;
	private Nodo origin;
	private Nodo destination;

	private int peso = 0; //Valore di default discriminante fra arco e arcoPN
	
	public Arco(Nodo o, Nodo d) {
		this.origin = o;
		this.destination = d;
		id = o.getId() + " -> " + d.getId();
	}
	
	public Nodo getOrigin() {
		return this.origin;
	}
	
	public Nodo getDestination() {
		return this.destination;
	}
	
	public String getID() {
		return this.id;
	}

	@Override
	public String toString() {
		if(peso == 0) {
		//Arco non PN
			return origin.getId() + "->" + destination.getId();
		}
		//Arco PN
		else{
			return 	origin.getId() + "->" + destination.getId() + " Peso: "+ peso;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Arco arco = (Arco) o;
		if (!this.getID().equals(arco.getID())) return false;
		if(this.getPeso() != arco.getPeso()) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(origin, destination);
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
}
