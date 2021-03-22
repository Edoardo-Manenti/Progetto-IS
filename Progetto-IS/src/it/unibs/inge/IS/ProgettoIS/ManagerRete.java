package it.unibs.inge.IS.ProgettoIS;

import java.util.*;
import java.util.HashMap;

/*
 * CONSIDERAZIONE : Avrebbe senso separare model, view e control in tre package diversi
 */

public class ManagerRete {
    private IORete ioRete;
    private Menu mainMenu;
    private Menu creazioneRete;

    private Rete nuovaRete;


    public ManagerRete(IORete ioRete){
        this.ioRete = ioRete;
    }

    public void setMainMenu(Menu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setCreazioneRete(Menu creazioneRete) {
        this.creazioneRete = creazioneRete;
    }

    //TODO: Problema come so cosa l'utente sta inserendo? se un posto-transizione o una transizione-posto?
    public void creaRete(){
        //Attenzione ricordati di gestire i nomi uguali
        String nomeRete = InputDati.leggiStringaNonVuota("Inserire il nome della rete: ");
        nuovaRete = new Rete(nomeRete);
        this.loopCreaRete();

    }
    
    /*
       *
     * MODELLO 2:
     * 
     * A ogni passo chiedo se si vuole aggiungere un nuovo posto, 
     *        aggiungere una transizione, aggiungere un arco, eliminare nodo, eliminare arco, terminare le modifiche
     * se nuovo posto:
     * - chiedo id
     * - controllo che non esista
     * 
     * se nuova transizione:
     * - chiedo id
     * - controllo che non esista
     * 
     * se nuovo arco:
     * - chiedo origine e destinazione
     * - controllo che esistano ENTRAMBI
     * - controllo che siano di diverso tipo
     * - controllo che l'arco non sia già presente
     * - aggiungo arco
     * 
     * se elimina arco/nodo -> chiedo id ed elimino
     * 
     * se termina modifiche:
     * - controllo che ogni transizione abbia almeno un precedente E almeno un successivo
     * - controllo che ogni posto abbia almeno un precedento O un successivo
     * - altrimenti informo del Nodo che non rispetta tale condizione e ritorno allo stadio di modifica
     * - salvo rete
     * 
     */
    
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
                creaRete();
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
    public void loopCreaRete(){
        int scelta;
        do{
            scelta = creazioneRete.scegli();
            this.creazioneReteScelte(scelta);
        }while (scelta != 0 & scelta != 5) ;
    }

    private void creazioneReteScelte(int scelta) {
        switch (scelta){
            case 0:
                aggiungiPosto();
                break;
            case 1:
                aggiungiTransizione();
                break;
            case 2:
                aggiungiArco();
                break;
            case 3:
                eliminaNodo();
                break;
            case 4:
                eliminaArco();
                break;
            case 5:
                terminaModifiche();
                break;
        }
    }

    private void terminaModifiche() {
        //Check correttezza rete

        boolean salvata;
        do{
            String nomeSalvataggio = InputDati.leggiStringaNonVuota("Inserire il nome del file con cui salvare la rete: ");
            salvata = (ioRete.salvaRete(nuovaRete, nomeSalvataggio));
            if (!(salvata)) System.out.println("Nome non corretto, già in uso");
        }while(!salvata);

        System.out.println("Rete salvata.");

    }
}
