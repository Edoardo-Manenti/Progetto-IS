package it.unibs.inge.IS.ProgettoIS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author edoardo
 *
 */
public class IORete {
	private JsonUtils jsonUtil;
	private String wdPath = "Reti";
	private File wd;
	private String separatore; 
	private HashMap<String,File> fileSalvati;
	private HashMap<String,Rete> retiSalvate;
	
	public IORete() {
		this.jsonUtil = new JsonUtils();
		this.separatore = File.separator;
		this.wd = new File(wdPath);
		if(!wd.exists()) {
			wd.mkdir();
		}
		else {
			caricaFileSalvati();
		}
	}

	public void setPath(String path) {
		this.wdPath = path;
	}
	
	private void caricaFileSalvati() {
		fileSalvati = new HashMap<String, File>();
		retiSalvate = new HashMap<String, Rete>();
		for(File f : wd.listFiles()) {
			String name = f.getName();
			fileSalvati.put(name, f);
			retiSalvate.put(name, caricaRete(name));
		}
	}
	
	public List<String> getNomiRetiSalvate() {
		return new ArrayList<String>(fileSalvati.keySet());
	}
	
	public int numeroRetiSalvate() {
		return fileSalvati.size();
	}
	
	public void salvaRete(Rete rete, String nomeRete) {
		String json = jsonUtil.compilaJson(rete);
		if(!isNuovaRete(rete)) {
			return;
		}
		File file = new File(wdPath+separatore+nomeRete);
		try ( FileWriter writer = new FileWriter(file))
		{
			writer.write(json);
			fileSalvati.put(nomeRete, file);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
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
		try ( Scanner scanner = new Scanner(fileSalvati.get(reteRichiesta)) 
				)
		{
			String json = "";
			while(scanner.hasNextLine()) {
				json += scanner.nextLine();
			}
			rete = this.jsonUtil.parsaJson(json);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return rete;
	}
	
	public boolean rinominaRete(String nomeRete, String nuovoNome) {
		if(!fileSalvati.containsKey(nomeRete)) {
			return false;
		}
		else {
			File rete = fileSalvati.get(nomeRete);
			return rete.renameTo(new File(nuovoNome));
		}
	}
}
