package modelPN;


import model.Arco;
import model.Nodo;
import model.Rete;
import model.Posto;
import model.Transizione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RetePN extends Rete{
    //INIZIO della Versione 2
    // - Pesi agli archi -> creare classe ArcoPN x
    // - Classe Token -> avrà un'istanza dentro la classe NodoPN ??
    //  - identificare marcatura corrente: usando i token contenuti nei nodi PN
    // - Override dei metodi aggiungiarco etcetc
    // - Metodo che genera in automatico da ReteN a RetePN
    // - Nel ManagerRete aggiungere importazione reteN
    // - Gestire il salvataggio delle retiPN


	public RetePN(String id, ArrayList<Arco> archi, HashMap<String, Nodo> nodi){
		super(id,archi,nodi);
		convertiAPN();
	}

	public RetePN( RetePN r) {
		super(r.getID(), r.getArchi(), r.getNodi());
	}
	
	public RetePN(Rete r) {
		super(r.getID(), r.getArchi(), r.getNodi());
		convertiAPN();
	}
	
	
	private void convertiAPN(){
			//Imposto la marcatura a zero
			for (Nodo n: this.getNodi().values()){
				if(n instanceof Posto) ((Posto) n).setToken(0);
			}
			//Imposto tutti gli archi a peso 1
			for(Arco a: this.getArchi()){
				a.setPeso(1);
			}
	}
	
	public void setPesoArco(String id, int peso) {
		for (Arco a :this.getArchi()) {
			if(a.getID().equals(id)){
				a.setPeso(peso);
				break;
			}
		}
	}
	public void setToken(String id, int token){
		Nodo n = this.getNodi().get(id);
		((Posto)n).setToken(token);
	}

	//VERSIONE 3: da togliere prima di branchare versione 2
	// return null se non ci sono transizioni attive
	public List<Transizione> transizioniAttive(){
	// 1) trovare le transizioni
	// 2) isoliamo gli archi entranti.
	// token in origin >= peso dell'arco
	//3) se tutti gli archi sono attivi -> transizione abilitata
		ArrayList<Transizione> transAbilitate = new ArrayList<>();
		for (Nodo n : this.getNodi().values()){
			if(n.isPosto()){
				continue;
			}
			ArrayList<Arco> archiEntranti = new ArrayList<>();
			for(Arco arco : this.getArchi()) {
				if(arco.getDestination().equals(n)){
					archiEntranti.add(arco);
				}
			}

			boolean transAbilitata = true;
			for(Arco arco : archiEntranti) {
			Posto origin = (Posto)arco.getOrigin();
			int nToken = origin.getToken();
			if(nToken < arco.getPeso()) transAbilitata = false;
			}
			if(transAbilitata) transAbilitate.add((Transizione) n);
		}
		return transAbilitate;
	}
	

	public void scattaTransizione(Transizione t) {
		//1)in Origin: nToken = nTokenOld - pesoArco
		//2)in Destination: nToken  = nTokenOld + pesoArco
		//3) ripetere per ogni arco entrante e uscente.

		ArrayList<Arco> archiEntranti = new ArrayList<>();
		for(Arco arco : this.getArchi()) {
			if(arco.getDestination().equals(t)){
				archiEntranti.add(arco);
			}
		}
		ArrayList<Arco> archiUscenti = new ArrayList<>();
		for(Arco arco : this.getArchi()) {
			if(arco.getOrigin().equals(t)){
				archiUscenti.add(arco);
			}
		}
 		//TODO: Come gestisco se la transizione non ha archi uscenti?
		// Cancello solo i token e li faccio sparire come in un pilone della Salerno-Reggio Calabria?

		for(Arco a : archiEntranti) {
			Posto p = (Posto)a.getOrigin();
			int nToken = p.getToken();
			p.setToken(nToken - a.getPeso());
		}
		for(Arco a : archiUscenti) {
			Posto p = (Posto)a.getDestination();
			int nToken = p.getToken();
			p.setToken(nToken + a.getPeso());
		}
	}

 	 //TODO: vedere i metodi sopra
	public boolean hasTransizioniAbilitate() {
		return transizioniAttive().size() > 0;
	}


	@Override
	public boolean equals(Object obj) {
		return super.equals((Rete)obj);
	}
}
