package it.unibs.inge.IS.ProgettoIS;

public class IORete {
	private JsonUtils jsonUtil;
	private String wdPath;
	
	// Il path di default è la directory da cui è stato lanciato il programma
	public IORete() {
		this.jsonUtil = new JsonUtils();
	}
	
	public void setPath(String path) {
		this.wdPath = path;
	}
	/*
	 * TO-DO LIST:
	 * 
	 *   - Metodo per caricare una rete gi� salvata 
	 *   - Metodo per salvare una rete 
	 */
	
	public void salvaRete(Rete rete) {
		this.jsonUtil
	}
	
	
	
}
