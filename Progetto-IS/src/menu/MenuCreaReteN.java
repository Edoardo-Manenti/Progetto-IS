package menu;

import model.Arco;
import model.Nodo;
import model.Rete;
import utils.IORete;
import utils.InputDati;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuCreaReteN {
    private static final IORete ioRete = new IORete();
    private static final Menu creazioneRete = new Menu("\n Cosa desidera fare?", new String[]{"Aggiungi posto",
            "Aggiungi transizione", "Aggiungi arco", "Elimina nodo", "Elimina arco", "Termina modifiche"});

    private Rete nuovaRete;
    private boolean isFinita = false;
    public MenuCreaReteN(Rete nuovaRete){
        this.nuovaRete = nuovaRete;
    }
    //Gestione Loop di creazione
    // TODO: una rete pu� essere formata da un solo nodo e una sola transizione.
    public void loopCreaReteN(){
        int scelta;
        do{
            scelta = creazioneRete.scegli();
            creazioneReteScelte(scelta);
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
                if (!(salvata)) System.out.println("Rete gi� presente in locale");
            } while (!salvata);

            System.out.println("Rete salvata.");
        }
        else{
            //La rete non è corretta
            //showErrors()
            isFinita = false;
            System.out.println("La rete presenta delle incorrettezze e' necessario modificarla");
        }
    }
}
