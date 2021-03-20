package it.unibs.inge.IS.ProgettoIS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author edoardo
 *
 */
public class IORete {
	private JsonUtils jsonUtil;
	private String wdPath = "Reti";
	private String separatore; 
	
	// Il path di default è la directory da cui è stato lanciato il programma
	public IORete() {
		this.jsonUtil = new JsonUtils();
		this.separatore = File.separator;
		File wd = new File(wdPath);
		if(!wd.exists()) {
			wd.mkdir();
		}
	}

	public void setPath(String path) {
		this.wdPath = path;
	}
	
	public void salvaRete(Rete rete, String nomeRete) {
		String json = jsonUtil.compilaJson(rete);
		File file = new File(wdPath+separatore+nomeRete);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		try ( FileWriter writer = new FileWriter(file))
		{
			writer.write(json);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public Rete caricaRete(String reteRichiesta) {
		Rete rete = new Rete();
		try ( Scanner scanner = new Scanner(new File(wdPath+separatore+reteRichiesta)) 
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
}
