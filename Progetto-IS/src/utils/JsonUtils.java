package utils;

import java.io.IOException;
import java.util.HashMap;

import model.*;
import modelPN.RetePN;

import modelPNP.RetePNP;
import org.json.*;


/**
 * 
 * @author edoardo
 *
 */
public  class JsonUtils {
	private static final IORete io = new IORete();
	public static String compilaJson(Rete rete) {
		if(rete.isPN()) compilaJson((RetePN) rete, rete.getID());
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("object", "RETE");
		String idRete = rete.getID();
		if(idRete != "") {
			jsonObj.put("idrete", rete.getID());
		}
		JSONArray array = new JSONArray();
		for(Nodo n : rete.getNodi().values()) {
			JSONObject elem = new JSONObject();
			elem.put("id", n.getId());
			elem.put("type", (n.isPosto() ? "POSTO" : "TRANSIZIONE"));
			JSONArray prec = new JSONArray();
			for(Nodo p : n.getPrec()) prec.put(p.getId());
			JSONArray succ = new JSONArray();
			for(Nodo s : n.getSucc()) succ.put(s.getId());
			elem.put("prec", prec);
			elem.put("succ",  succ);
			array.put(elem);
			}
		jsonObj.put("nodi", array);	
		return jsonObj.toString();
	}
	
	public static String compilaJson(RetePN rete, String retePortante) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("object", "RETE_PN");
		jsonObj.put("retePortante", retePortante + ".json");
		JSONArray arrayPesi = new JSONArray();
		for(Arco arco : rete.getArchi()) {
			JSONObject elem = new JSONObject();
			elem.put("arco", arco.getID());
			elem.put("peso", arco.getPeso());
			arrayPesi.put(elem);
		}
		jsonObj.put("pesi", arrayPesi);
		JSONArray arrayMarcatura = new JSONArray();
		for(Nodo n : rete.getNodi().values()) {
			if(n instanceof Posto) {
				Posto p = (Posto)n;
				JSONObject obj = new JSONObject();
				obj.put("nodo", p.getId());
				obj.put("n_token", p.getToken());
				arrayMarcatura.put(obj);
			}
		}
		jsonObj.put("marcatura", arrayMarcatura);
		return jsonObj.toString();
	}
	
	// per retePN: controllo che pesi e token > 0
	public static Rete parsaJson(String jsonString) throws IOException {
		JSONObject jsonObj = new JSONObject(jsonString);
		String tipoRete = jsonObj.getString("object");
		//controllo che sia un formato RETE
		if(tipoRete.equals("RETE")){
			return parsaReteN(jsonString);
		}
		else if(tipoRete.equals("RETE_PN")) {
			//rete
			return parsaRetePN(jsonString);
		}
		else if(tipoRete.equals("RETE_PNP")) {
			//rete PnP
			return parsaRetePNP(jsonString);
		}
		else throw new IOException("Formato JSON incorretto");
	}
	
	
	//controllo pesi e token > 0
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

	private static RetePNP parsaRetePNP(String jsonString) {
		JSONObject jsonObj = new JSONObject(jsonString);
		RetePN retePN = io.caricaRete(jsonObj.getString("retePortante"));
		if (retePN == null) throw new Exception("Rete portante non presente in forma persistente");
		RetePNP rete = new RetePNP(retePN);
		HashMap<Transizione, Integer> mappa_priority = new HashMap<>();
		JSONArray lista_priorità = jsonObj.getJSONArray("priority_list");
		for (Object elem : lista_priorità) {
			JSONObject trans = (JSONObject) elem;
			Transizione t = rete.getNodo(trans.getString("id"));
			int priorità = trans.getInt("priority");
			mappa_priority.put(t, priorità);
		}
		rete.setPriorità(mappa_priority);
		return rete;
	}

	private static void parsaArchi(JSONArray array, Rete rete, Nodo n, boolean precedenti) {
		for(Object nodo : array) {
			String p = (String)nodo;
			if(!rete.containsNodo(p)) {
				Nodo origin;
				if(n.isPosto()) {
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
