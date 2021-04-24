package model;

import java.util.Objects;

public class Posto extends Nodo {
	private int token = -1; //valore di default discriminante fra Nodo e Nodo PN

	public Posto(String id) {
		super(id);
		super.setPosto(true);
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
	public int hashCode() {
		return Objects.hash(token);
	}
}
