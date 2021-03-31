package it.unibs.inge.IS.ProgettoIS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
	private String wdPath = "Reti";
	private File wd;
	private String separatore;
	private HashMap<String, File> fileSalvati;
	
	
	public IOUtils() {
		this.separatore = File.separator;
		this.wd = new File(wdPath);
		this.fileSalvati = new HashMap<String, File> ();
		if(!wd.exists()) wd.mkdir();
		else caricaFileSalvati();
	}
	
	
	public void setPath(String path) {
		this.wdPath = path;
	}
	
	public List<String> getNomiFileSalvati() {
		return new ArrayList<String>(fileSalvati.keySet());
	}
	
	private void caricaFileSalvati() {
		for(File f : wd.listFiles()) {
			String name = f.getName();
			fileSalvati.put(name, f);
		}
	}
	
	public boolean salvaFile(String nomeFile, String contenutoFile) {
		File file = new File(wdPath+separatore+nomeFile + ".json");
		try ( FileWriter writer = new FileWriter(file))
		{
			writer.write(contenutoFile);
			fileSalvati.put(nomeFile+".json", file);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public String caricaFile(String nomeFile) throws IOException{
		Scanner scanner = new Scanner(fileSalvati.get(nomeFile));
		String json = "";
		while(scanner.hasNextLine()) {
			json += scanner.nextLine();
		}
			return json;
	}
	
	
	public boolean rinominaFile(String nomeFile, String nuovoNome) {
		if(!fileSalvati.containsKey(nomeFile)) return false;
		else {
			File rete = fileSalvati.get(nomeFile);
			return rete.renameTo(new File(nuovoNome));
		}
	}
}
