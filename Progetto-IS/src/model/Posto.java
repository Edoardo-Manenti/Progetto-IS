package model;

import java.util.Objects;

public class Posto extends Nodo {
	private int token = -1; //valore di default discriminante fra Nodo e Nodo PN

	public Posto(String id) {
		super(id);
	}

	@Override
	public TipoNodo getType() {
		return TipoNodo.POSTO;
	}

	public void aggiungiPrec(Transizione t) {
		super.aggiungiPrec(t);
	}
	
	public void aggiungiSucc(Transizione t) {
		super.aggiungiSucc(t);
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Posto posto = (Posto) o;
		return token == posto.token;
	}

	@Override
    //TODO: come risolviamo il problema della marcatura??
	// quando faccio il toString dell'arco viene richimato il toString dei posti il quale a sua volta riporta la marcatura
	public String toString() {
		String toReturn = super.toString();
		if(token != -1) toReturn += " marcatura: " + token;
		return toReturn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(token);
	}
}
