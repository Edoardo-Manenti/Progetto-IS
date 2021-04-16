package utils;

import model.Rete;
import modelPN.RetePN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author edoardo
 *
 */
public class IORete {
	private IOUtils io;
	private HashMap<String, Rete> retiSalvate = new HashMap<>();
	
	public IORete() {
		io = new IOUtils();
		caricaRetiSalvate();
	}

	private void caricaRetiSalvate() {
		for (String nome : io.getNomiFileSalvati()) {
			Rete r = caricaRete(nome);
			if(r != null) retiSalvate.put(nome, r);
		}
	}
	
	public void setPath(String path) {
		io.setPath(path);
	}
	
	public List<String> getNomiRetiN() {
		ArrayList<String> lista = new ArrayList<>();
		for(String s : retiSalvate.keySet()) {
			if(!retiSalvate.get(s).isPN()) lista.add(s);
		}
		return lista;
	}

	public List<String> getNomiRetiPN() {
		ArrayList<String> lista = new ArrayList<>();
		for(String s : retiSalvate.keySet()) {
			if(retiSalvate.get(s).isPN()) lista.add(s);
		}
		return lista;
	}

	public int numeroRetiSalvate() {
		caricaRetiSalvate();
		return retiSalvate.size();
	}
	
	public boolean salvaRete(Rete rete) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtils.compilaJson(rete);
			boolean flag = io.salvaFile(rete.getID(), json);
			if (flag) retiSalvate.put(rete.getID(), rete);
			return true;
		}
	}

	public boolean salvaRetePN(RetePN rete, String retePortante) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtils.compilaJson(rete, retePortante);
			boolean flag = io.salvaFile(rete.getID(), json);
			if (flag) retiSalvate.put(rete.getID(), rete);
			return true;
		}
	}

	private boolean isNuovaRete(Rete nuovaRete) {
		caricaRetiSalvate();
		for(Rete r : retiSalvate.values()) {
			if(r.equals(nuovaRete)) {
				return false;
			}
		}
		return true;
	}
	public boolean isNuovaRete(String rete){
		caricaRetiSalvate();
		return !retiSalvate.containsKey(rete); //Se non è contenuta è nuova
	}
	
	public Rete caricaRete(String reteRichiesta) {
		Rete rete;
		try {
			String json = io.caricaFile(reteRichiesta);
			rete = JsonUtils.parsaJson(json);
		}
		catch(IOException exc) {
			exc.printStackTrace();
			return null;
		}
		return rete;
	}
	
	public boolean rinominaRete(String nomeRete, String nuovoNome) {
		return io.rinominaFile(nomeRete, nuovoNome);
	}
}
