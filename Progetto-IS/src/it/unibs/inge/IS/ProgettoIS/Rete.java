package it.unibs.inge.IS.ProgettoIS;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author edoardo
 *
 */
public class Rete {

	private HashMap<String, Nodo> nodi;
	private ArrayList<Arco> archi;
	private String id;
	private static int idPosto = 0;
	private static int idTransizione = 1;
	
	public Rete() {
		this("");
	}
	
	public Rete(String id) {
		this.id = id;
		this.nodi = new HashMap<String, Nodo>();
		this.archi = new ArrayList<Arco>();
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
	/**
	 * 
	 * @param p
	 */
	private boolean aggiungiPosto(Posto p) {
		if (this.containsNodo(p)) return false;
		else {
			this.nodi.put(p.getId(), p);
			return true;
		}
	}

	private boolean aggiungiTransizione(Transizione t) {
		if (this.containsNodo(t)) return false;
		else {
			this.nodi.put(t.getId(), t);
			return true;
		}
	}

	//Interfaccia pubblica
	public boolean creaNodo(String nome, boolean isPosto){
		boolean result;
		if(isPosto) result = aggiungiPosto(new Posto(nome));
		else result = aggiungiTransizione(new Transizione(nome));
		return result;
	}

	/*
	 * @param nomeNodo nomeNodo nome del nodo da cercare
	 */
	public Nodo getNodo(String nomeNodo) {
		return nodi.get(nomeNodo);
	}
	
	public HashMap<String, Nodo> getNodi(){
		return this.nodi;
	}
	
	public ArrayList<Arco> getArchi(){
		return this.archi;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.equals((Rete) obj);
	}
	
	public boolean equals(Rete rete2) {
		if(this.archi.size() != rete2.getArchi().size()) {
			return false;
		}
		ArrayList<Arco> archi2 = rete2.getArchi();
		for(Arco a : archi) {
			if(!archi2.contains(a)) continue;
		}
		return true;
	}
	
	public boolean eliminaNodo(String id) {
		return (nodi.remove(id) != null); 
	}
	
	public boolean eliminaArco(Arco arco) {
		return this.archi.remove(arco);
	}
	
	public String getID() {
		return this.id;
	}

	// TO-DO list :
	/*
	 * Metodo per creare una rete
	 * Non basta il costruttore?
	 *  Alla fine creiamo le strutture dati e poi guidiamo nella creazione ...
	 *  Se l'utente vuole salvare una rete con meno di una transizione e un posto -> messaggio di errore
	 */
	
	/*
	 * Metodo per creare un nuovo Posto
	 */
	
	public void crea_nuovo_Posto(Transizione t) {
		
		Posto newP = new Posto(""+idPosto);
		idPosto = idPosto + 2;
		aggiungiArco(new Arco(t, newP));
	}
	
	/*
	 * Metodo per creare una nuova Transizione
	 */
//	public void crea_nuova_Transizione() {
//		
//		Transizione newT = new Transizione("" + idTransizione);
//		idTransizione = idTransizione+2;
//		Posto po = scelto_da_utente;
//		aggiungiArco(new Arco(po, newT));
//		
//		if (utente_sceglie_di_creare_un_nuovo_posto) {
//			crea_nuovo_Posto(newT);
//			
//		} else {
//			Posto pd = scelto_da_utente;
//			aggiungiArco(new Arco(newT, pd));
//			
//		}
//	}
	
	/*
	 * Metodo per testare se due reti sono uguali
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id).append("\n");
		for (Arco a:archi)
			sb.append(a.toString()).append("\n");

		return sb.toString();
	}

	public boolean controllaCorrettezza(){
		if(nodi.isEmpty()) return false;
		for(Nodo n : nodi.values()) {
			boolean flag;
			if(n.isPosto()) {
				flag = (n.numberOfPrec() > 0) || (n.numberOfSucc() > 0);
			}
			else {
				flag = (n.numberOfPrec() > 0 && (n.numberOfSucc() > 0));
			}
			if(!flag) return false;
		}
		return true;
	}
}
