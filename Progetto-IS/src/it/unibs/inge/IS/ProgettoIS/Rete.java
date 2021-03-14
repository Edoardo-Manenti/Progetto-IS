package it.unibs.inge.IS.ProgettoIS;

import java.util.ArrayList;
import java.util.HashMap;

public class Rete {
	/*
	 * Classe di modellazione della Rete, contiene tutte le informazioni riguardanti i nodi e le transizioni
	 */
	private HashMap<String, Nodo> nodi;
	private ArrayList<Arco> archi;
	private IORete managerIO;
	
	public Rete() {
		this.nodi = new HashMap<String, Nodo>();
		this.archi = new ArrayList<Arco>();
		this.managerIO = new IORete();
	}
	
	public boolean containsNodo(String n) {
		return nodi.containsKey(n);
	}

	public boolean containsNodo(Nodo n) {
		return nodi.containsValue(n);
	}
	
	public void aggiungiArco(Arco arco) {
		Nodo o = arco.getOrigin();
		Nodo d = arco.getDestination();

		if(!(nodi.containsValue(o))) 
		{ 
			nodi.put(o.getId(), o);
		}

		if(!(nodi.containsValue(d))) {
			nodi.put(d.getId(), d);
		}
		
		this.archi.add(arco); 
		if(o.isPosto()) { 
			Posto p = (Posto)arco.getOrigin();
			Transizione t = (Transizione)arco.getDestination();
			p.aggiungiSucc(t);
			t.aggiungiPrec(p);
		}
		else {
			Transizione t = (Transizione)arco.getOrigin(); 
			Posto p = (Posto)arco.getDestination();
			t.aggiungiSucc(p); 
			p.aggiungiPrec(t);
		}
	}
	
	public void aggiungiPosto(Posto p) {
		this.nodi.put(p.getId(), p);
	}

	public void aggiungiTransizione(Transizione t) {
		this.nodi.put(t.getId(), t);
	}
	
	/*
	 * @param nomeNodo nome del nodo da cercare
	 */
	public Nodo getNodo(String nomeNodo) {
		return nodi.get(nomeNodo);
	}
	
	public HashMap<String, Nodo> getNodi(){
		return this.nodi;
	}

	// TO-DO list :
	/*
	 * Metodo per creare una rete
	 */
	
	/*
	 * Metodo per creare un nuovo Posto
	 */
	
	/*
	 * Metodo per creare una nuova Transizione
	 */
	
	/*
	 * Metodo per testare se due reti sono uguali
	 */
}
