package it.unibs.inge.IS.ProgettoIS;

import model.Rete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author edoardo
 *
 */
public class IORete {
	private IOUtils io;
	private HashMap<String, Rete> retiSalvate;
	
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
	
	public List<String> getNomiRetiSalvate() {
		return new ArrayList<String>(retiSalvate.keySet());
	}
	
	public int numeroRetiSalvate() {
		return retiSalvate.size();
	}
	
	public boolean salvaRete(Rete rete, String nomeFile) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtils.compilaJson(rete);
			return io.salvaFile(nomeFile, json);
		}
	}

	private boolean isNuovaRete(Rete nuovaRete) {
		for(Rete r : retiSalvate.values()) {
			if(r.equals(nuovaRete)) {
				return false;
			}
		}
		return true;
	}
	
	public Rete caricaRete(String reteRichiesta) {
		Rete rete = new Rete();
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
