package it.unibs.inge.IS.ProgettoIS;

import java.util.HashMap;
import java.util.List;

public abstract class Nodo {
	private String id;	
	private boolean isPosto, isTransizione = false;
	private HashMap<String, Nodo> prec;
	private HashMap<String, Nodo> succ;
	
	public Nodo (String id) {
		this.id = id;
		prec = new HashMap<String, Nodo>();
		succ = new HashMap<String, Nodo>();
	}
	
	protected void aggiungiPrec(Nodo n) {
		this.prec.put(n.getId(), n);
	}

	protected void aggiungiSucc(Nodo n) {
		this.succ.put(n.getId(), n);
	}
	
	protected List<Nodo> getPrec() {
		return (List<Nodo>)this.prec.values();
	}

	protected List<Nodo> getSucc() {
		return (List<Nodo>)this.succ.values();
	}

	protected void setPosto(boolean p) {
		this.isPosto = p;
	}
	
	protected void setTransizione(boolean t) {
		this.isTransizione = t;
	}

	public boolean isPosto() {
		return isPosto;
	}
	
	public boolean isTransizione() {
		return isTransizione;
	}

	public String getId() {
		return this.id;
	}
}
