package model;

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
}
