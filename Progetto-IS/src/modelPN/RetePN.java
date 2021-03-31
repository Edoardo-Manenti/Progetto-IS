package modelPN;


import model.Arco;
import model.Nodo;
import model.Rete;
import model.Posto;

import java.util.ArrayList;
import java.util.HashMap;


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
		setPN();
	}
	
	
	public RetePN(Rete r) {
		super(r.getID(), r.getArchi(), r.getNodi());
		convertiAPN();
		setPN();
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
			//Qui c'è da fare il calcolo per le transizioni
			//TODO: retePN.settaTransizioni();
			settaTransizioni();
	}
	
	public void setPesoArco(Arco arco) {};
	
	
	private void settaTransizioni() {
		
	}
	
	//VERSIONE 3: da togliere prima di branchare versione 2
//	public List<Transizione> transizioniAttive(){
//		//controllare 
//	}
	
//
//	public boolean scattaTransizione(Transizione t) {
//		// sposta i token
//	}
	
	@Override
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		//controlla nome dei nodi, gli archi, e i pesi degli archi;
	}
}
