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
			return origin.toString() + "->" + destination.toString();
		}
		//Arco PN
		else{
			return 	origin.toString() + "->" + destination.toString() + " Peso: "+ peso;
		}
	}

	//TODO: Adattare a rete PN
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Arco arco = (Arco) o;
		return Objects.equals(origin, arco.origin) && Objects.equals(destination, arco.destination);
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
