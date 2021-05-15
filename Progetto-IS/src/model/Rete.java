package model;

import modelPN.RetePN;

import java.util.ArrayList;
import java.util.HashMap;

public class Rete {

	private HashMap<String, Nodo> nodi;
	private ArrayList<Arco> archi;
	private String id;

	//Edo usando questa variabile scrive il parser ->
	// isPn == true allora devi scrivere un file .json per PN con i pesi etc etc
	
	public Rete() {
		this("");
	}
	
	public Rete(String id) {
		this.id = id;
		this.nodi = new HashMap<String, Nodo>();
		this.archi = new ArrayList<Arco>();
	}

	public Rete(String id, ArrayList<Arco> archi, HashMap<String, Nodo> nodi){
		this.id = id;
		this.archi = archi;
		this.nodi = nodi;
	}
	
	public boolean isPN() {
		return this instanceof RetePN;
	}

	//Interfaccia pubblica
	public boolean creaNodo(String nome, boolean isPosto){
		boolean result;
		if(isPosto) result = aggiungiPosto(new Posto(nome));
		else result = aggiungiTransizione(new Transizione(nome));
		return result;
	}

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

	public boolean aggiungiArco(Arco arco) {
		//Controllo se già esiste
		if (this.archi.contains(arco)) {
			return false;
		}
		else if(arco.getOrigin().isPosto() == arco.getDestination().isPosto()){
			return false; //Controllo diversità
		}
		else
		{
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
			return true; //Aggiunto correttamente
		}
	}

	public boolean eliminaNodo(String id) {
		return (nodi.remove(id) != null);
	}

	public boolean eliminaArco(Arco arco) {
		return this.archi.remove(arco);
	}
	
	public Arco getArco(String id) {
		for(Arco a : this.archi) {
			if (a.getID().equals(id))
				return a;
		}
		return null;
	}

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
			if(!archi2.contains(a)) return false;
		}
		return true;
	}

	public boolean containsNodo(String n) {
		return nodi.containsKey(n);
	}

	public boolean containsNodo(Nodo n) {
		return containsNodo(n.getId());
	}

	public String getID() {
		return this.id;
	}


	//TODO: Questo metodo è da rivedere per gestire RetiPN e RetiPNp
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + this.id).append("\n\n");
		sb.append("NODI:").append("\n");
		for (Nodo n : nodi.values()) {
			sb.append(n.toString()).append("\n");
		}
		sb.append("\n").append("ARCHI:").append("\n");
		for (Arco a:archi)
			sb.append(a.toString()).append("\n");

		return sb.toString();
	}

	public boolean controllaCorrettezza(){
		if(nodi.isEmpty()) return false;
		for(Nodo n : nodi.values()) {
			boolean flag;
			flag = (n.numberOfPrec() > 0) || (n.numberOfSucc() > 0);
			if(!flag) return false;
		}
		return true;
	}
}
