package it.unibs.inge.IS.ProgettoIS;

import java.io.IOException;

import org.json.*;

import com.sun.org.apache.bcel.internal.util.BCELifier;

import jdk.nashorn.internal.parser.JSONParser;

public class JsonUtils {
	// Metodo per la compilazione della string Json secondo il modello 1
	// Da testare
	public String compilaJson(Rete rete) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("object", "RETE");
		JSONArray array = new JSONArray();
		for(Nodo n : rete.getNodi().values()) {
			JSONObject elem = new JSONObject();
			elem.put("type", (n.isPosto() ? "POSTO" : "TRANSIZIONE"));
			elem.put("id", n.getId());
			JSONArray prec = new JSONArray();
			for(Nodo p : n.getPrec()) prec.put(p.getId());
			JSONArray succ = new JSONArray();
			for(Nodo s : n.getSucc()) succ.put(s.getId());
			elem.put("prec", prec);
			elem.put("succ",  succ);
			}
		jsonObj.put("rete", array);	
		return jsonObj.toString();
	}
	
	public Rete parsaJson(String jsonString) throws IOException {
		JSONObject jsonObj = new JSONObject(jsonString);
		//controllo che sia un formato RETE
		if(!jsonObj.get("object").equals("RETE")){
			Rete rete = new Rete();
			JSONArray array = jsonObj.getJSONArray("rete");
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
				for(Object nodo : prec) {
					String p = (String)nodo;
					if(!rete.containsNodo(p)) {
						Nodo origin;
						if(n.isPosto()) {
							origin = new Transizione(p);
						}
						else {
							origin = new Posto(p);
						}
						rete.aggiungiArco(new Arco(origin, n));
					}
					else {
						rete.aggiungiArco(new Arco(rete.getNodo(p),n));
					}
				}
				JSONArray succ = obj.getJSONArray("succ");
				for(Object nodo : succ) {
					String s = (String)nodo;
					if(!rete.containsNodo(s)) {
						Nodo dest;
						if(n.isPosto()) {
							dest = new Transizione(s);
						}
						else {
							dest = new Posto(s);
						}
						rete.aggiungiArco(new Arco(n, dest));
					}
					else {
						rete.aggiungiArco(new Arco(n, rete.getNodo(s)));
					}
				}
			}
		}
		throw new IOException("Formato JSON incorretto");
	}
	private void parsaArchi(JSONArray array, Rete rete, Nodo n, boolean precedenti) {
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
				rete.aggiungiArco(new Arco(rete.getNodo(p),n));
			}
		}
	}
}
