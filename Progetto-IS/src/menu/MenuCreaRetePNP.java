package menu;

import model.Arco;
import model.Nodo;
import model.Rete;
import model.Transizione;
import modelPN.RetePN;
import modelPNP.RetePNP;
import utils.IORete;
import utils.InputDati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MenuCreaRetePNP {
    private static final IORete ioRete = new IORete();
    private static final Menu modificaRete =
            new Menu("Stai creando una nuova rete PNP\nCosa desidera fare?", new String[]{"Modifica priorità", "Termina modifiche"});

    private RetePNP retePNP;
    private String nomeRetePN;
    private boolean isFinita = false;

    public MenuCreaRetePNP(RetePN rete, String nomeRetePN){
        retePNP = new RetePNP(rete);
        this.nomeRetePN = nomeRetePN;
    }

    public void loopCreaRetePNP(){
        int scelta;
        do{
            scelta = modificaRete.scegli();
            if(scelta == 1){
                modificaPriorità();
            }
            else if(scelta == 2){
                terminaModifiche();
            }
            if(isFinita) break;
        }while (scelta != 0);
        //il loop termina se la variabile isFinita viene messa a true, cioè
        // se l'utente ha selezionato terminaModifiche e il controllo Ã¨ andato a buon fine
        isFinita = false;
    }

    private void modificaPriorità() {
        List<Transizione> listaTransizioni = retePNP.getTransizioni();
        HashMap<Transizione, Integer> priorità = retePNP.getPriority();
        System.out.println("Le transizioni attualmente nella rete sono i seguenti: ");

        for (int i=0; i<listaTransizioni.size(); i++)
        {
            System.out.println( (i) + "\t" + listaTransizioni.get(i) + "- priority :" + priorità.get(listaTransizioni.get(i)));
        }
        int scelta = InputDati.leggiIntero("Digitare il numero della transizione per modificarne la priorità (digitare -1 per non modificarne nessuno) >",
                -1, listaTransizioni.size()-1);

        if(scelta >= 0){
            Transizione daModificare = listaTransizioni.get(scelta);
            int valore = InputDati.leggiIntero("Inserire il nuovo valore di priorità per la transizione "+ daModificare, 1, Integer.MAX_VALUE);
            retePNP.modificaPriorita(daModificare, valore);
            System.out.println("La priorità di " + daModificare + "è stata modificata");
        }else{
            System.out.println("Nessuna modifica apportata.");
        }
    }


    private void terminaModifiche() {
        //Non serve il check correttezza della rete in quanto il modo in cui ho salvato i dati e ho fatto interagire l'utente
        //evita che si creino situazioni inconsistenti
        isFinita = true;
        boolean salvata;
        salvata = (ioRete.salvaRetePNP(retePNP, nomeRetePN));
        if (!(salvata)) {
            System.out.println("\nRete identica strutturalmente a una già presente in locale. Impossibile inserire");
            isFinita = false;
        }
        else System.out.println("\nRete salvata.");
    }
}
