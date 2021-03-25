package model;

public class Transizione extends Nodo {
	
	public Transizione(String id) {
		super(id);
		super.setTransizione(true);
	}
	
	public void aggiungiPrec(Posto p) {
		super.aggiungiPrec(p);
	}
	
	public void aggiungiSucc(Posto p) {
		super.aggiungiSucc(p);
	}
}
