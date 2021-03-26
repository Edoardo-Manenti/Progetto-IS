package it.unibs.inge.IS.ProgettoIS;

import model.Arco;
import model.Nodo;
import model.Rete;

import java.util.*;
import java.util.HashMap;


public class ManagerRete {
    private IORete ioRete;
    private Menu mainMenu;
    private Menu creazioneRete;


    private Rete nuovaRete;
    private boolean isFinita = false;


    public ManagerRete(IORete ioRete){
        this.ioRete = ioRete;
    }

    public void setMainMenu(Menu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setCreazioneRete(Menu creazioneRete) {
        this.creazioneRete = creazioneRete;
    }


    public void creaReteN(){
        //Attenzione ricordati di gestire i nomi uguali
        String nomeRete = InputDati.leggiStringaNonVuota("Inserire il nome della rete: ");
        nuovaRete = new Rete(nomeRete);
        this.loopCreaReteN();

    }

    
    public void visualizzaRete(){
        int nrretiSalvate = ioRete.numeroRetiSalvate();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiSalvate();
            for (int i=0; i<listaReti.size(); i++)
            {
                System.out.println( (i) + "\t" + listaReti.get(i));
            }
           int scelta = InputDati.leggiIntero("Digitare il numero della rete da visualizzare >",
                   0, nrretiSalvate-1);
            String reteDaVisualizzare = listaReti.get(scelta);
            System.out.println("\n" + ioRete.caricaRete(reteDaVisualizzare).toString());
        }


    }
    //Gestione MainLoop
    public void mainLoop() {
        int scelta;
        do{
            scelta = mainMenu.scegli();
            this.elabora(scelta);
        }while (scelta != 0);
    }

    public void elabora(int scelta){
        switch (scelta) {
            case 1:
                creaReteN();
                break;
            case 2:
                visualizzaRete();
                break;
            default:
                ;
                break;
        }
    }

    //Gestione Loop di creazione
    public void loopCreaReteN(){
        int scelta;
        do{
            scelta = creazioneRete.scegli();
            this.creazioneReteScelte(scelta);
            if(isFinita) break;
        }while (scelta != 0);
        //il loop termina se la variabile isFinita viene messa a true, cioè se l'utente ha selezionato terminaModifiche e il controllo è andato a buon fine
        isFinita = false;
    }

    private void creazioneReteScelte(int scelta) {
        switch (scelta){
            case 1:
                aggiungiPosto();
                break;
            case 2:
                aggiungiTransizione();
                break;
            case 3:
                aggiungiArco();
                break;
            case 4:
                eliminaNodo();
                break;
            case 5:
                eliminaArco();
                break;
            case 6:
                terminaModifiche();
                break;
        }
    }
    private void aggiungiPosto(){
        boolean aggiunto = false;
        do{
            String idNodo = InputDati.leggiStringaNonVuota("Inserire il nome del posto da aggiungere: ");
            aggiunto = nuovaRete.creaNodo(idNodo, true);
            if (!(aggiunto)) System.out.println("Nome non corretto, già in uso. Riprovare");
        }while (!aggiunto);
        System.out.println("Il posto è stato aggiunto correttamente.");
    }

    private void aggiungiTransizione(){
        boolean aggiunto = false;
        do{
            String idTransizione = InputDati.leggiStringaNonVuota("Inserire il nome della transizione da aggiungere: ");
            aggiunto = nuovaRete.creaNodo(idTransizione, false);
            if (!(aggiunto)) System.out.println("Nome non corretto, già in uso. Riprovare");
        }while (!aggiunto);
        System.out.println("La transizione è stato aggiunta correttamente.");
    }
    private void aggiungiArco(){
        boolean contenuto = false;

        Nodo o = null;
        do{
            String origine = InputDati.leggiStringaNonVuota("Inserire il nome del nodo origine dell'arco: ");
            contenuto = nuovaRete.containsNodo(origine);
            if (contenuto) o = nuovaRete.getNodo(origine);
            else System.out.println("Nodo non presente. Riprovare");
        }while (!contenuto);

        contenuto = false;

        Nodo d = null;
        do{
            String destinazione = InputDati.leggiStringaNonVuota("Inserire il nome del nodo destinazione dell'arco: ");
            contenuto = nuovaRete.containsNodo(destinazione);
            if (contenuto) d = nuovaRete.getNodo(destinazione);
            else System.out.println("Nodo non presente. Riprovare");
        }while (!contenuto);
        Arco arco = new Arco(o, d);
        if(nuovaRete.aggiungiArco(arco))
            System.out.println("L'arco "+ arco.toString() + " è stato aggiunto correttamente");
        else{
            System.out.println("L'arco "+ arco.toString() + " era già presente o non valido");
        }
    }
    private void eliminaNodo(){
        HashMap<String, Nodo> mappa = nuovaRete.getNodi();
        if(mappa.isEmpty()){
            System.out.println("Non ci sono nodi salvati");
            return;
        }
        System.out.println("I nodi presenti attualmente nella rete sono i seguenti: ");
        for (String id : nuovaRete.getNodi().keySet()) {
            System.out.println(id + "\t" + mappa.get(id).toString());
        }
        String scelta = InputDati.leggiStringaNonVuota("Digitare l'id del nodo da eliminare (inserire nome non valido per non eliminare nulla)>");
        if (mappa.containsKey(scelta)){ //Se hai inserito il valore corretto bella
        Nodo daEliminare = mappa.get(scelta);
        boolean elimina = InputDati.yesOrNo("Sicuro di voler eliminare il nodo "+ daEliminare.toString() + "?");
        if(elimina){
                nuovaRete.eliminaNodo(daEliminare.getId());
                System.out.println("Nodo eliminato.");
            }else{
                System.out.println("Nodo non eliminato.");
            }
        }else{
            System.out.println("Errore di digitazione. Riprovare");
        }

    }
    private void eliminaArco(){
        ArrayList<Arco> listaArchi = nuovaRete.getArchi();
        if(listaArchi.isEmpty()){
            System.out.println("Non ci sono archi salvati");
            return;
        }
        System.out.println("Gli archi attualmente nella rete sono i seguenti: ");

        for (int i=0; i<listaArchi.size(); i++)
        {
            System.out.println( (i) + "\t" + listaArchi.get(i));
        }
        int scelta = InputDati.leggiIntero("Digitare il numero dell'arco da eliminare (digitare -1 per non eliminare nessuno) >",
                -1, listaArchi.size()-1);
        Arco daEliminare;
        if(scelta >= 0){
            daEliminare = listaArchi.get(scelta);
            boolean elimina = InputDati.yesOrNo("Sicuro di voler eliminare l'arco "+ daEliminare.toString() + "?");
            if(elimina){
                nuovaRete.eliminaArco(daEliminare);
                System.out.println("Arco eliminato.");
            }else{
                System.out.println("Arco non eliminato.");
            }
        }else{
            System.out.println("Nessun arco è stato eliminato.");
        }

    }
    private void terminaModifiche() {
        //Check correttezza rete
        if (nuovaRete.controllaCorrettezza()) {
            //La rete è corretta TOP
            isFinita = true;
            boolean salvata;
            do {
                String nomeSalvataggio = InputDati.leggiStringaNonVuota("Inserire il nome del file con cui salvare la rete: ");
                salvata = (ioRete.salvaRete(nuovaRete, nomeSalvataggio));
                if (!(salvata)) System.out.println("Rete già presente in locale");
            } while (!salvata);

            System.out.println("Rete salvata.");
        }
        else{
            //La rete non è corretta
            //showErrors()
            isFinita = false;
            System.out.println("La rete presenta delle incorrettezze è necessario modificarla");
        }

    }
}
