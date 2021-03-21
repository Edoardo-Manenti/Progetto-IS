package it.unibs.inge.IS.ProgettoIS;

public class Main {
    public static void main(String[] args){
        //Inizializzare menù

        Menu menu = new Menu("Buongiorno, questo software permette tramite interfaccia testuale di inserire, visualizzare e modificare delle reti di Petri. " +
                "\n Cosa desidera fare?", new String[]{"Crea nuova rete", "Visualizza reti salvate"});
        menu.loop();
        System.out.println("PROGRAMMA TERMINATO");
    }
}
