package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public abstract class Nodo {
	private String id;
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

	public int numberOfPrec() {
		return prec.size();
	}
	
	public int numberOfSucc() {
		return succ.size();
	}
	
	public List<Nodo> getPrec() {
		return new ArrayList<Nodo>(this.prec.values());
	}

	public List<Nodo> getSucc() {
		return new ArrayList<Nodo>(this.succ.values());
	}

	//COMMENTO principio SOLID LISKOV SUBSTITUTION: la classe padre Nodo possedeva due attributi booleani per discriminare il tipo dei suoi oggetti figli
	//ragionamento simile a mantenere una variabile type. Il fatto che poi ci fossero due metodi isPosto e isTransizione erano una violazione del principio di sostituzione in quanto le due sottoclassi
	// non erano sostituibili l'una con l'altra. ( Avevamo metodi specifici che lavoravano per la sottoclasse discriminando però il tipo usando attributi della superclasse :/ )
	public abstract TipoNodo getType();

	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return (getType().toString())+ ":" + this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (getClass() != o.getClass()) return false;
		Nodo nodo2 = (Nodo)o;
		if(this.id != nodo2.getId()) return false;
		if(!this.prec.keySet().equals(nodo2.prec.keySet())) return false;
		if(!this.succ.keySet().equals(nodo2.succ.keySet())) return false;
		return true;
	}
}
