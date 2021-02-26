package it.unibs.inge.IS.ProgettoIS;

class Posto extends Nodo {

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
}
