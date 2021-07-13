package menu;

import model.TipoRete;
import utils.IORete;
import utils.InputDati;

import java.io.IOException;
import java.util.List;

public class MainMenu {
    //COMMENTO: SINGLETON -> ultima fase: sostituisco le chiamate al costruttore con la chiamata a IORete.getInstance()
    private static final IORete ioRete = IORete.getInstance();

    private static final Menu mainMenu =
            new Menu("Buongiorno, questo software permette tramite interfaccia testuale di inserire, " +
                                                  "visualizzare e modificare delle reti di Petri. " +
                                                  "\n Cosa desidera fare?",
                    new String[]{"Crea nuova rete N", "Visualizza reti N salvate",
                            "Visualizza reti PN salvate", "Visualizza reti PNP salvate",
                            "Crea nuova rete PN", "Mostra evoluzione di una rete PN", "Crea nuova rete PNP",
                            "Mostra evoluzione di una rete PNP", "Importa rete"});

    //Gestione MainLoop
    public static void mainLoop() {
        int scelta;
        do{
            scelta = mainMenu.scegli();
            elabora(scelta);
        }while (scelta != 0);
    }
    public static void elabora(int scelta){
        switch (scelta) {
            case 1:
                creaReteN();
                break;
            case 2:
                visualizzaRetiPerTipo(TipoRete.RETEN);
                break;
            case 3:
                visualizzaRetiPerTipo(TipoRete.RETEPN);
                break;
            case 4:
                visualizzaRetiPerTipo(TipoRete.RETEPNP);
                break;
            case 5:
                creaRetePN();
                break;
            case 6:
                evoluzioneRete(TipoRete.RETEPN);
                break;
            case 7:
                creaRetePNP();
                break;
            case 8:
                evoluzioneRete(TipoRete.RETEPNP);
                break;
            case 9:
                importazioneRete();
            default:
                ;
                break;
        }
    }
    public static void creaReteN(){
        //Attenzione ricordati di gestire i nomi uguali
        String nomeRete;
        boolean salvata = false;
        do {
            nomeRete = InputDati.
                    leggiStringaNonVuota("Inserire il nome della rete: ");
            if(ioRete.isNuovaRete(nomeRete)) salvata = true;
            else System.out.println("Nome già utilizzato. Inserirne un'altro");
        } while (!salvata);
        //Commento: delego la creazione della reteN appurato che non ci sono reti nel DB con quel nome alla classe MenuCreaReteN
        new MenuCreaReteN(nomeRete).loopCreaReteN();
    }

    private static void creaRetePN() {
        //Qui faccio selezionare all'utente la rete N di riferimento e la passo a MenuCreaRetePN
        List<String> listaReti = ioRete.getRetiPerTipo(TipoRete.RETEN);
        String reteSelezionata = InputDati.selezionaElementoDaLista(listaReti,
                "Digitare il numero della rete da selezionare come Rete N di riferimento >");
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        //COMMENTO SUL MODEL-VIEW Separation: nell'ambito della creazione delle reti abbiamo rimosso alcuni compiti dalla classe MainMenu delegandoli agli opportuni
        // sottomenu che interagiscono direttamente con le classi di modello. Di conseguenza MainMenu risulta "libera" da comopiti di interazione diretta con le classi modellistiche.
        new MenuCreaRetePN(reteSelezionata).loopCreaRetePN();
    }
    private static void creaRetePNP() {
        List<String> listaReti = ioRete.getRetiPerTipo(TipoRete.RETEPN);
        String reteSelezionata = InputDati.selezionaElementoDaLista(listaReti,
                "Digitare il numero della rete da selezionare come Rete PN di riferimento >");
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        new MenuCreaRetePNP(reteSelezionata).loopCreaRetePNP();
    }


    private static void importazioneRete() {
        String path = InputDati.leggiStringaNonVuota("Digitare la path assoluta della rete da importare: ");
        try {
            if(ioRete.importaRete(path)) System.out.println("Rete importata.");
        } catch (IOException e) {
          System.out.println("Errore di importazione della rete: " + e.getMessage());
        }
    }
    private static void evoluzioneRete(TipoRete t){
        List<String> listaReti = ioRete.getRetiPerTipo(t);
        int nrretiSalvate = listaReti.size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            String reteDaEvolvere = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete per cui mostrare l'evoluzione >");
            new MenuEvoluzioneRetePN(ioRete.caricaRete(reteDaEvolvere)).loopEvoluzione();
        }
    }


    //COMMENTO pattern SOLID OPEN-CLOSED! perfetto esempio: questi metodi di visualizzazione sfruttano 3 metodi diversi della classe IORete per ottenere la lista
    //di reti della tipologia specificata, questo crea una forte dipendenza fra le due classi. Infatti se aggiungessimo una nuova tipologia di rete dovremmo scrivere 2 nuovi metodi mentre se si adottasse una soluzione più
    //polimorfica si potrebbe ridurre l'accoppiamento tra le classi e renderle più facilmente estendibili. --> adesso ho un solo metodo che gestisce la moltitudine di reti in modo molto generico e scalabile

    private static void visualizzaRetiPerTipo(TipoRete t){
        int nrretiSalvate = ioRete.getRetiPerTipo(t).size();

        if(nrretiSalvate == 0) System.out.println("Non ci sono reti salvate di tipo " + t.toString() +" al momento");
        else{
            List<String> listaReti = ioRete.getRetiPerTipo(t);
            String reteDaVisualizzare = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete da visualizzare >");
            System.out.println("\n" + ioRete.caricaRete(reteDaVisualizzare).toString());
        }
    }

}
