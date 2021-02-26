package it.unibs.inge.IS.ProgettoIS;

import java.util.HashMap;

public class Rete {
	/*
	 * Classe di modellazione della Rete, contiene tutte le informazioni riguardanti i nodi e le transizioni
	 */
	private HashMap<String, Nodo> nodi;
	
	public Rete() {
		this.nodi = new HashMap<String, Nodo>();
	}
	
	public boolean containsNodo(Nodo n) {
		return nodi.containsValue(n);
	}
	
	public boolean aggiungiTransizione(Posto p, Transizione t) {
		if(!nodi.containsValue(p)) {
			p.aggiungiSucc(t);
			t.aggiungiPrec(p);
			return true;
		}
		return false;
	}
	
	public boolean aggiungiPosto(Transizione t, Posto p) {
		if(!nodi.containsValue(t)) {
			t.aggiungiSucc(p);
			p.aggiungiPrec(t);
			return true;
		}
		return false;
	}
	
	/*
	 * @param nomeNodo nome del nodo da cercare
	 */
	public Nodo getNodo(String nomeNodo) {
		return nodi.get(nomeNodo);
	}
}
