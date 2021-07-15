package menu;

import model.*;
import modelPN.RetePN;
import utils.IORete;
import utils.InputDati;
import utils.PetriNetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MenuCreaRetePN {
    //Singleton
    private final IORete ioRete;
    private static final Menu creazioneRete =
            new Menu("Stai creando una nuova rete PN\nCosa desidera fare?", new String[]{"Modifica peso arco",
            "Modifica Token", "Termina modifiche"});

    private RetePN nuovaRete;
    private String nomeReteN;
    private boolean isFinita = false;

    public MenuCreaRetePN() throws PetriNetException {
        ioRete = IORete.getInstance();
        setup();
    }

    public void setup() throws PetriNetException {
        //Qui faccio selezionare all'utente la rete N di riferimento e la passo a MenuCreaRetePN
        List<String> listaReti = ioRete.getRetiPerTipo(TipoRete.RETEN);
        String reteSelezionata = InputDati.selezionaElementoDaLista(listaReti,
                "Digitare il numero della rete da selezionare come Rete N di riferimento >");
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        //COMMENTO SUL MODEL-VIEW Separation: nell'ambito della creazione delle reti abbiamo rimosso alcuni compiti dalla classe MainMenu delegandoli agli opportuni
        // sottomenu che interagiscono direttamente con le classi di modello. Di conseguenza MainMenu risulta "libera" da comopiti di interazione diretta con le classi modellistiche.
        Rete reteN = ioRete.caricaRete(reteSelezionata);
        nuovaRete = new RetePN(reteN);
        this.nomeReteN = reteSelezionata;
    }
    public void loop(){
        int scelta;
        do{
            scelta = creazioneRete.scegli();
            creazioneReteScelte(scelta);
            if(isFinita) break;
        }while (scelta != 0);
        //il loop termina se la variabile isFinita viene messa a true, cioè
        // se l'utente ha selezionato terminaModifiche e il controllo Ã¨ andato a buon fine
        isFinita = false;
    }

    private void creazioneReteScelte(int scelta) {
        switch (scelta){
            case 1:
               modificaPesoArco();
                break;
            case 2:
                modificaToken();
                break;
            case 3:
                terminaModifiche();
                break;
        }
    }

    private void modificaToken() {
         List<Nodo> lista_posti = nuovaRete.getNodi().values().
                 stream().filter((n)-> n.getType().equals(TipoNodo.POSTO)).collect(Collectors.toList());

        System.out.println("I posti presenti attualmente nella rete sono i seguenti: ");
        for (int i=0; i<lista_posti.size(); i++)
        {
            System.out.println( (i) + "\t" + lista_posti.get(i));
        }
        int scelta = InputDati.leggiIntero("Digitare il numero del posto da modificare (digitare -1 per non modificarne nessuno) >",
                -1, lista_posti.size()-1);

        String daModificare;
        if(scelta >= 0){
            int nuovoT = InputDati.leggiInteroPositivo("Digitare il nuovo valore del token >");
            daModificare = lista_posti.get(scelta).getId();
            nuovaRete.setToken(daModificare, nuovoT);
            System.out.println("\nToken modificato");
        }else{
            System.out.println("\nNessun token modificato.");
        }

    }

    private void modificaPesoArco() {
        ArrayList<Arco> listaArchi = nuovaRete.getArchi();
        System.out.println("Gli archi attualmente nella rete sono i seguenti: ");

        for (int i=0; i<listaArchi.size(); i++)
        {
            System.out.println( (i) + "\t" + listaArchi.get(i));
        }
        int scelta = InputDati.leggiIntero("Digitare il numero dell'arco da modificare (digitare -1 per non modificarne nessuno) >",
                -1, listaArchi.size()-1);
        String daModificare;
        if(scelta >= 0){
            daModificare = listaArchi.get(scelta).getID();
            int nuovoPeso = InputDati.leggiInteroPositivo("Digitare il nuovo peso dell'arco >");
            nuovaRete.setPesoArco(daModificare, nuovoPeso);
            System.out.println("\nArco modificato");
        }else{
            System.out.println("\nNessun arco modificato.");
        }
    }

    private void terminaModifiche() {
        //Non serve il check correttezza della rete in quanto il modo in cui ho salvato i dati e ho fatto interagire l'utente
        //evita che si creino situazioni inconsistenti
            isFinita = true;
            boolean salvata = false;
        try {
            salvata = (ioRete.salvaRetePN(nuovaRete, nomeReteN));
        } catch (PetriNetException e) {
            System.out.println(e.getMessage());
        }
        if (!(salvata)) {
                System.out.println("\nRete identica strutturalmente a una già presente in locale. Impossibile inserire");
                isFinita = false;
            }
            else System.out.println("\nRete salvata.");
    }
}
