package utils;

import model.Rete;
import modelPN.RetePN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author edoardo
 *
 */
public class IORete {
	private IOUtils io;

	public IORete() {
		io = new IOUtils();
	}

	public List<String> getNomiRetiN() {
		ArrayList<String> lista = new ArrayList<>();
		for(String s : io.getNomiFileSalvati()) {
			Rete r = caricaRete(s);
			if(r!= null && (r.getType().equals("ReteN"))) lista.add(s);
		}
		return lista;
	}

	public List<String> getNomiRetiPN() {
		ArrayList<String> lista = new ArrayList<>();
		for(String s : io.getNomiFileSalvati()) {
			Rete r = caricaRete(s);
			if(r!=null && r.getType().equals("RetePN")) lista.add(s);
		}
		return lista;
	}

	//TODO: getNomiRetiPNP()
	public List<String> getNomiRetiPNP() {
		ArrayList<String> lista = new ArrayList<>();
		for(String s : io.getNomiFileSalvati()) {
			Rete r = caricaRete(s);
			if(r != null && r.getType().equals("RetePNP")) lista.add(s);
		}
		return lista;
	}


	public int numeroRetiSalvate() {
		return io.getNomiFileSalvati().size();
	}
	
	public boolean salvaRete(Rete rete) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtils.compilaJson(rete);
			return io.salvaFile(rete.getID(), json);
		}
	}

	public boolean salvaRetePN(RetePN rete, String retePortante) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtils.compilaJson(rete, retePortante);
			return io.salvaFile(rete.getID() + "_PN", json);
		}
	}

	private boolean isNuovaRete(Rete nuovaRete) {
	    ArrayList<Rete> retiSalvate = new ArrayList<>();
	    for(String s : io.getNomiFileSalvati()){
	        Rete r = caricaRete(s);
	        if (r != null) {
	        	retiSalvate.add(r);
			}
		}
		for(Rete r : retiSalvate) {
			if((r.getType().equals(nuovaRete.getType())) && r.equals(nuovaRete)) {
				return false;
			}
		}
		return true;
	}
	public boolean isNuovaRete(String rete){
	    return !io.getNomiFileSalvati().contains(rete);
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
