package it.unibs.inge.IS.ProgettoIS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
		return new ArrayList<Nodo>(this.prec.values());
	}

	protected List<Nodo> getSucc() {
		return new ArrayList<Nodo>(this.succ.values());
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
	
	@Override
	public String toString() {
		return (isPosto ? "Posto" : "Transizione")+ ":" + this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Nodo nodo = (Nodo) o;
		return isPosto == nodo.isPosto &&
				isTransizione == nodo.isTransizione &&
				Objects.equals(id, nodo.id) && Objects.equals(prec, nodo.prec) &&
				Objects.equals(succ, nodo.succ);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isPosto, isTransizione, prec, succ);
	}
}
