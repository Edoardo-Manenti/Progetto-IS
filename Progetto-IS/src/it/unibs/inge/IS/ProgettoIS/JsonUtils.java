package it.unibs.inge.IS.ProgettoIS;

import java.io.IOException;

import org.json.*;


/**
 * 
 * @author edoardo
 *
 */
public class JsonUtils {
	
	public String compilaJson(Rete rete) {
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
	
	public Rete parsaJson(String jsonString) throws IOException {
		JSONObject jsonObj = new JSONObject(jsonString);
		//controllo che sia un formato RETE
		if(jsonObj.getString("object").equals("RETE")){
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
		else throw new IOException("Formato JSON incorretto");
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
				if(precedenti)
					rete.aggiungiArco(new Arco(rete.getNodo(p),n));
				else
					rete.aggiungiArco(new Arco(n, rete.getNodo(p)));
			}
		}
	}
}
