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
	private HashMap<String,File> retiSalvate;
	
	public IORete() {
		this.jsonUtil = new JsonUtils();
		this.retiSalvate = caricaRetiSalvate();
		this.separatore = File.separator;
		this.wd = new File(wdPath);
		if(!wd.exists()) {
			wd.mkdir();
		}
	}

	public void setPath(String path) {
		this.wdPath = path;
	}
	
	private HashMap<String, File> caricaRetiSalvate() {
		HashMap<String, File> retiSalvate = new HashMap<String, File>();
		for(File f : wd.listFiles()) {
			retiSalvate.put(f.getName(), f);
		}
		return retiSalvate;
	}
	
	public List<String> getNomiRetiSalvate() {
		return new ArrayList<String>(retiSalvate.keySet());
	}
	
	public int numeroRetiSalvate() {
		return retiSalvate.size();
	}
	
	public void salvaRete(Rete rete, String nomeRete) {
		String json = jsonUtil.compilaJson(rete);
		File file = new File(wdPath+separatore+nomeRete);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			else {
				file = retiSalvate.get(nomeRete);
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		try ( FileWriter writer = new FileWriter(file))
		{
			writer.write(json);
			retiSalvate.put(nomeRete, file);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public Rete caricaRete(String reteRichiesta) {
		Rete rete = new Rete();
		try ( Scanner scanner = new Scanner(retiSalvate.get(reteRichiesta)) 
				)
		{
			String json = "";
			while(scanner.hasNextLine()) {
				json += scanner.nextLine();
			}
			System.out.println(json);
			rete = this.jsonUtil.parsaJson(json);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return rete;
	}
	
	public boolean rinominaRete(String nomeRete, String nuovoNome) {
		if(!retiSalvate.containsKey(nomeRete)) {
			return false;
		}
		else {
			File rete = retiSalvate.get(nomeRete);
			return rete.renameTo(new File(nuovoNome));
		}
	}
}
