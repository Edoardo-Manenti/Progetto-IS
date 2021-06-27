package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    private IOContext ioContext;

	public IOUtils() {
		this.ioContext = new IOContext();
	}

	public List<String> getNomiFileSalvati() {
		return new ArrayList<>(ioContext.getFileSalvati().keySet());
	}

	public boolean salvaFile(String nomeFile, String contenutoFile) {
		nomeFile = makeUnique(nomeFile);
		//TODO: rendi riga sottostante un metodo
		//forse: rendi anche makeUnique un metodo di IOContext
		String filePath = ioContext.getWdPath()+ioContext.getSeparatore() + nomeFile + ioContext.getExtension();
		File file = new File(filePath);
		try ( FileWriter writer = new FileWriter(file))
		{
			writer.write(contenutoFile);
			this.ioContext.addFile(nomeFile + ioContext.getExtension(), file);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	public String makeUnique(String nomeFile) {
		String nuovoNome = nomeFile;
		String filePath = ioContext.getWdPath()+ioContext.getSeparatore() + nuovoNome + ioContext.getExtension();
		File file = new File(filePath);
		int i = 1;
		while(file.exists()){
			nuovoNome = nomeFile + "(" + i + ")";
			filePath = ioContext.getWdPath()+ioContext.getSeparatore() + nuovoNome + ioContext.getExtension();
			file = new File(filePath);
			i++;
		}
		return nuovoNome;
	}
	
	public String caricaFile(String nomeFile) throws IOException{
		Scanner scanner = new Scanner(IOContext.fileSalvati.get(nomeFile));
		StringBuilder json = new StringBuilder();
		while(scanner.hasNextLine()) {
			json.append(scanner.nextLine());
		}
		return json.toString();
	}

	public boolean rinominaFile(String nomeFile, String nuovoNome) {
	    HashMap<String, File> fileSalvati = ioContext.getFileSalvati();
		if(!fileSalvati.containsKey(nomeFile)) return false;
		else {
			File rete = fileSalvati.get(nomeFile);
			return rete.renameTo(new File(nuovoNome));
		}
	}
}
