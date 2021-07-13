package utils;

import java.io.IOException;
import java.util.HashMap;

import model.*;
import modelPN.RetePN;

import modelPNP.RetePNP;
import org.json.*;


/**
 * Classe adibita a compilare e parsare le stringhe json che descrivono le reti
 * @author edoardo
 *
 */
public class JsonUtilsParser {
	private static final IORete io = new IORete();


	/**
	 * Dato il contenuto json viene creato l'oggetto Rete corrispondente
 	 * @param jsonString
	 * @throws IOException nel caso in cui il formato del file json contenga errori
	 */
	public static Rete parsaJson(String jsonString) throws IOException {
		JSONObject jsonObj = new JSONObject(jsonString);
		String tipoRete = jsonObj.getString("object");
		TipoRete t = TipoRete.valueOf(tipoRete);
		//controllo che sia un formato RETE
		if(t.equals(TipoRete.RETEN)){
			return parsaReteN(jsonString);
		}
		else if(t.equals(TipoRete.RETEPN)) {
			//rete
			return parsaRetePN(jsonString);
		}
		else if(t.equals(TipoRete.RETEPNP)) {
			//rete PnP
			return parsaRetePNP(jsonString);
		}
		else throw new IOException("Formato JSON incorretto");
	}

	/**
	 * Parsa il json per formare una retePN
	 * @param jsonString
	 * @throws IOException nel caso di:
	 * 						- archi con pesi negativi
	 * 						- marcatura negativa su un nodo
	 */
	private static RetePN parsaRetePN(String jsonString) throws IOException {
		JSONObject jsonObj = new JSONObject(jsonString);
		Rete reteN = io.caricaRete(jsonObj.getString("retePortante"));
		RetePN retePN = new RetePN(reteN);
		JSONArray array = jsonObj.getJSONArray("pesi");
		for(Object elem : array) {
			JSONObject obj = (JSONObject) elem;
			Arco arco = retePN.getArco(obj.getString("arco"));
			int peso = obj.getInt("peso");
			if(arco != null && peso > 0) {
				arco.setPeso(peso);
			}
			else throw new IOException("JSON incorretto: archi con pesi negativi");
		}
		JSONArray marcatura = jsonObj.getJSONArray("marcatura");
		for(Object elem : marcatura) {
			JSONObject obj = (JSONObject) elem;
			Posto p = (Posto)retePN.getNodo(obj.getString("nodo"));
			int nToken = obj.getInt("n_token");
			if(p != null && nToken >= 0) p.setToken(nToken);
			else throw new IOException("JSON incorretto: marcatura sintatticamente sbagliata");
		}
		return retePN;
	}

	/**
	 * Parsa il json relativo ad una reteN
 	 * @param jsonString
	 * @return ReteN corrispondente
	 */
	private static Rete parsaReteN(String jsonString) {
		JSONObject jsonObj = new JSONObject(jsonString);
		Rete rete = jsonObj.has("idrete") ? new Rete(jsonObj.getString("idrete")) : new Rete();
			JSONArray array = jsonObj.getJSONArray("nodi");
			for(Object elem : array) {
				JSONObject obj = (JSONObject)elem;
				Nodo n;
				String id = obj.getString("id");

				if(!rete.containsNodo(id)) {
					if(obj.get("type").equals("POSTO")) {
						n = new Posto(id);
					}
					else {
						n = new Transizione(id);
					}
				}
				else n = rete.getNodo(id);

				JSONArray prec = obj.getJSONArray("prec");
				parsaArchi(prec, rete, n, true);
			}
			return rete;
	}

	/**
	 * Parsa reti di tipo PNP
	 * @param jsonString
	 * @return
	 * @throws IOException nel caso in cui la rete portante non è salvata in maniera persistente
	 */
	private static RetePNP parsaRetePNP(String jsonString) throws IOException{
		JSONObject jsonObj = new JSONObject(jsonString);
		RetePN retePN = (RetePN) io.caricaRete(jsonObj.getString("retePortante"));
		if (retePN == null)
				throw new IOException("Rete portante non presente in forma persistente");
		RetePNP rete = new RetePNP(retePN);
		HashMap<Transizione, Integer> mappa_priority = new HashMap<>();
		JSONArray lista_priorita = jsonObj.getJSONArray("priority_list");
		for (Object elem : lista_priorita) {
			JSONObject trans = (JSONObject) elem;
			Transizione t = (Transizione) rete.getNodo(trans.getString("id"));
			int priorita = trans.getInt("priority");
			mappa_priority.put(t, priorita);
		}
		rete.setPriority(mappa_priority);
		return rete;
	}

	/**
	 * Parsa gli archi di una reteN e li aggiunge alla rete
	 * @param array Array di oggetti JSON contenenti gli archi
	 * @param rete rete a cui si devono aggiungere gli archi
	 * @param n
	 * @param precedenti indica se sto parsando gli archi entranti, o gli archi uscenti dal nodo
	 */
	private static void parsaArchi(JSONArray array, Rete rete, Nodo n, boolean precedenti) {
		for(Object nodo : array) {
			String p = (String)nodo;
			if(!rete.containsNodo(p)) {
				Nodo origin;
				if(n.getType().equals(TipoNodo.POSTO)) {
					origin = new Transizione(p);
				}
				else {
					origin = new Posto(p);
				}
				if(precedenti)
					rete.aggiungiArco(new Arco(origin, n));
				else 
					rete.aggiungiArco(new Arco(n, origin));
			}
			else {
				if(precedenti)
					rete.aggiungiArco(new Arco(rete.getNodo(p),n));
				else
					rete.aggiungiArco(new Arco(n, rete.getNodo(p)));
			}
		}
	}
}
