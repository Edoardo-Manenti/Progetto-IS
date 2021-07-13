package utils;

import model.*;
import modelPN.RetePN;
import modelPNP.RetePNP;
import org.json.JSONArray;
import org.json.JSONObject;

//COMMENTO sul pattern HIGH COHESION: Abbiamo realizzato che la classe JsonUtil svolgeva compiti poco coesi, mescolando parsing e scrittura dei file json rappresentanti le reti.
//Spostando la scrittura si nota anche che questa classe NON fa uso di ioRete e quindi questa trasformazione supporta anche la riduzione dell'accoppiamento.
public class JsonUtilsWriter {
    /**
     * @param rete
     * 		Rete di cui si vuole compilare il json
     * @return json string
     *
     */
    public static String compilaJson(Rete rete) {
        if(rete.getType().equals(TipoRete.RETEPN)) compilaJson((RetePN) rete, rete.getID());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("object", rete.getType().toString());
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

    /**
     *
     * @param rete
     * @param retePortante
     * 		reteN su cui si basa la retePN di cui si vuole compilare il json
     * @return contenuto del file json
     */
    public static String compilaJson(RetePN rete, String retePortante) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("object", rete.getType().toString());
        jsonObj.put("retePortante", retePortante);
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

    /**
     *
     * @param rete
     * @param retePortante
     * 		retePN su cui si basa la rete PNP
     * @return json string
     */
    public static String compilaJson(RetePNP rete, String retePortante) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("object", rete.getType().toString());
        jsonObj.put("retePortante", retePortante);
        JSONArray arrayPriority = new JSONArray();
        var mappa_priority = rete.getPriority();
        for(Transizione t : mappa_priority.keySet()) {
            JSONObject trans = new JSONObject();
            trans.put("id", t.getId());
            trans.put("priority", mappa_priority.get(t));
            arrayPriority.put(trans);
        }
        jsonObj.put("priority_list", arrayPriority);
        return jsonObj.toString();
    }
}
