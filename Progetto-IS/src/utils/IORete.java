package utils;

import model.Rete;
import model.TipoRete;
import modelPN.RetePN;
import modelPNP.RetePNP;

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
	//COMMENTO pattern GoF Singleton. E' importante che l'oggetto IORete sia unico perchè è responsabile della
	// tracciabilità delle Reti presenti in locale. Avere istanze diverse di IORete può portare a incongruenze per cui
	// reti logicamente salvate ma non ancora fisicamente salvate non vengano mostrate tra le reti presenti in locale.
	private static IORete instance;

	// Singleton: rendo private il costruttore
	private IORete() {
		io = new IOUtils();
	}

	// Singleton: creo il metodo getInstance per accedere all' oggetto singleton
	public static IORete getInstance() {
		if(instance == null) {
			instance = new IORete();
		}
		return instance;
	}


	//COMMENTO principio Solid OPEN-CLOSED: Utilizzando un enum viene resa pi� semplice l'implementazione di eventuali nuove tipologie di
	// reti e inoltre le interdipendenze fra MainMenu e ioRete vengono ridotte ad un solo metodo per la visualizzazione. Prima infatti se
	// si desiderava creare un nuovo tipo di rete era necessario scrivere 2 metodi per fare ci� adesso � sufficiente scrivere la rete in modo che rispetti l'interfaccia e il software gestisce il resto
	public List<String> getRetiPerTipo(TipoRete t){
		ArrayList<String> lista = new ArrayList<>();
		for(String s : io.getNomiFileSalvati()) {
			Rete r = caricaRete(s);
			if(r!= null && (r.getType().equals(t))) lista.add(s);
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
			String json = JsonUtilsWriter.compilaJson(rete);
			return io.salvaFile(rete.getID(), json);
		}
	}

	public boolean salvaRetePN(RetePN rete, String retePortante) {
		if(!isNuovaRete(rete)) {
			return false;
		}
		else {
			String json = JsonUtilsWriter.compilaJson(rete, retePortante);
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

	public boolean importaRete(String path) throws IOException {
		String json = io.caricaFileEsterno(path);
		Rete rete = JsonUtilsParser.parsaJson(json);
		if(!isNuovaRete(rete)) throw new IOException("Rete gia' presente in locale");
		return io.salvaReteEsterna(path, json);
	}
	
	public Rete caricaRete(String reteRichiesta) {
		Rete rete;
		try {
			String json = io.caricaFile(reteRichiesta);
			rete = JsonUtilsParser.parsaJson(json);
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

	public boolean salvaRetePNP(RetePNP retePNP, String nomeRetePN) {
		if(!isNuovaRete(retePNP)) {
			return false;
		}
		else {
			String json = JsonUtilsWriter.compilaJson(retePNP, nomeRetePN);
			return io.salvaFile(retePNP.getID() + "_PNP", json);
		}
	}
}
