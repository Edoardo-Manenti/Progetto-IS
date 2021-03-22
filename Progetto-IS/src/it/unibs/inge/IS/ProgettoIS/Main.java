package it.unibs.inge.IS.ProgettoIS;

public class Main {
    public static void main(String[] args){
        //Inizializzare menù
        ManagerRete managerRete = new ManagerRete(new IORete());
        Menu mainMenu = new Menu("Buongiorno, questo software permette tramite interfaccia testuale di inserire, visualizzare e modificare delle reti di Petri. " +
                "\n Cosa desidera fare?", new String[]{"Crea nuova rete", "Visualizza reti salvate"});
        managerRete.setMainMenu(mainMenu);
        Menu creaReteM = new Menu("\n Cosa desidera fare?", new String[]{"Aggiungi posto", "Aggiungi transizione", "Aggiungi arco", "Elimina nodo", "Elimina arco", "Termina modifiche"});
        managerRete.setCreazioneRete(creaReteM);

        managerRete.mainLoop();
    }
}
