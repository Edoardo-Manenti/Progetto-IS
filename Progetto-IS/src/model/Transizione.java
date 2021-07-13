package model;

public class Transizione extends Nodo {
	private Boolean isActive; //La transizione sia attiva o meno

	//private boolean scattabile;
	
	public Transizione(String id) {
		super(id);
		isActive = null;
	}

	@Override
	public TipoNodo getType() {
		return TipoNodo.TRANSIZIONE;
	}

	public void aggiungiPrec(Posto p) {
		super.aggiungiPrec(p);
	}
	
	public void aggiungiSucc(Posto p) {
		super.aggiungiSucc(p);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

}
