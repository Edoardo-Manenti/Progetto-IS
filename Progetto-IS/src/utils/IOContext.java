package utils;

import java.io.File;
import java.util.HashMap;

public class IOContext {
    private String wdPath = "Reti";
    private File wd;
    private String extension = ".json";
    private String separatore;
    public static HashMap<String,File> fileSalvati;

    public IOContext() {
        this.separatore = File.separator;
        this.wd = new File(wdPath);
        this.fileSalvati = new HashMap<>();
        if(!wd.exists()) wd.mkdir();
        else caricaFileSalvati();
    }

    private void caricaFileSalvati() {
        for(File f : wd.listFiles()) {
            String name = f.getName();
            fileSalvati.put(name, f);
        }
    }

    public boolean containsFile(String name){
        return fileSalvati.containsKey(name);
    }

    public void addFile(String nomeFile, File file){
        this.fileSalvati.put(nomeFile, file);
    }

    public HashMap<String, File> getFileSalvati() {
        return this.fileSalvati;
    }

    public String getExtension(){
        return this.extension;
    }

    public File getWd() {
        return this.wd;
    }

    public String getWdPath() {
        return wdPath;
    }

    public String getSeparatore(){
        return this.separatore;
    }
}
