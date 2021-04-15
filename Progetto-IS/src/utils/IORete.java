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
		return retiSalvate.values().stream().filter(Rete::isPN).map(Rete::toString).collect(Collectors.toList());
	}

	public List<String> getNomiRetiPN() {
		return retiSalvate.values().stream().filter(rete ->
				!rete.isPN()).map(Rete::toString).collect(Collectors.toList());
	}

	public int numeroRetiSalvate() {
		caricaRetiSalvate();
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

	public boolean salvaRetePN(RetePN rete, String retePortante, String nomeFile) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtils.compilaJson(rete, retePortante);
			return io.salvaFile(nomeFile, json);
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
