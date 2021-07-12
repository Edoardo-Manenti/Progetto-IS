package menu;

import model.Rete;
import modelPN.RetePN;
import utils.IORete;
import utils.InputDati;

import java.io.IOException;
import java.util.List;

public class MainMenu {

    private static final IORete ioRete = new IORete();

    private static final Menu mainMenu =
            new Menu("Buongiorno, questo software permette tramite interfaccia testuale di inserire, " +
                                                  "visualizzare e modificare delle reti di Petri. " +
                                                  "\n Cosa desidera fare?",
                    new String[]{"Crea nuova rete N", "Visualizza reti N salvate",
                            "Visualizza reti PN salvate", "Visualizza reti PNP salvate",
                            "Crea nuova rete PN", "Mostra evoluzione di una rete PN", "Crea nuova rete PNP",
                            "Mostra evoluzione di una rete PNP", "Importa rete"});

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
        List<String> listaReti = ioRete.getNomiRetiN();
        String reteSelezionata = InputDati.selezionaElementoDaLista(listaReti,
                "Digitare il numero della rete da selezionare come Rete N di riferimento >");
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        //COMMENTO SUL MODEL-VIEW Separation: nell'ambito della creazione delle reti abbiamo rimosso alcuni compiti dalla classe MainMenu delegandoli agli opportuni
        // sottomenu che interagiscono direttamente con le classi di modello. Di conseguenza MainMenu risulta "libera" da comopiti di interazione diretta con le classi modellistiche.
        new MenuCreaRetePN(reteSelezionata).loopCreaRetePN();
    }
    private static void creaRetePNP() {
        List<String> listaReti = ioRete.getNomiRetiPN();
        String reteSelezionata = InputDati.selezionaElementoDaLista(listaReti,
                "Digitare il numero della rete da selezionare come Rete PN di riferimento >");
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        new MenuCreaRetePNP(reteSelezionata).loopCreaRetePNP();
    }



    //TODO: Discriminare fra N, PN e PNP -> Usare metodo intermedio
    public static void visualizzaReteN(){
        int nrretiSalvate = ioRete.getNomiRetiN().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiN();
            String reteDaVisualizzare = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete da visualizzare >");
            System.out.println("\n" + ioRete.caricaRete(reteDaVisualizzare).toString());
        }
    }

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
                visualizzaReteN();
                break;
            case 3:
                visualizzaRetiPN();
                break;
            case 4:
                visualizzaRetiPNP();
                break;
            case 5:
                creaRetePN();
                break;
            case 6:
                evoluzioneRetePN();
                break;
            case 7:
                creaRetePNP();
                break;
            case 8:
                evoluzioneRetePNP();
                break;
            case 9:
                importazioneRete();
            default:
                ;
                break;
        }
    }

    private static void importazioneRete() {
        String path = InputDati.leggiStringaNonVuota("Digitare la path assoluta della rete da importare: ");
        try {
            if(ioRete.importaRete(path)) System.out.println("Rete importata.");
        } catch (IOException e) {
          System.out.println("Errore di importazione della rete: " + e.getMessage());
        }
    }

    //TODO: Check se funziona anche con reti PNP
    private static void evoluzioneRetePNP() {
        int nrretiSalvate = ioRete.getNomiRetiPNP().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiPNP();
            String reteDaEvolvere = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete per cui mostrare l'evoluzione >");
            new MenuEvoluzioneRetePN(ioRete.caricaRete(reteDaEvolvere)).loopEvoluzione();
        }
    }

    private static void evoluzioneRetePN() {
        int nrretiSalvate = ioRete.getNomiRetiPN().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiPN();
            String reteDaEvolvere = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete per cui mostrare l'evoluzione >");
            new MenuEvoluzioneRetePN(ioRete.caricaRete(reteDaEvolvere)).loopEvoluzione();
        }
    }

    private static void visualizzaRetiPN() {
        int nrretiSalvate = ioRete.getNomiRetiPN().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiPN();
            String reteDaVisualizzare = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete da visualizzare >");
            System.out.println("\n" + ioRete.caricaRete(reteDaVisualizzare).toString());
        }
    }

    //TODO: Check con nuovo metodo di Edo
    private static void visualizzaRetiPNP() {
        int nrretiSalvate = ioRete.getNomiRetiPNP().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiPNP();
            String reteDaVisualizzare = InputDati.selezionaElementoDaLista(listaReti,
                    "Digitare il numero della rete da visualizzare >");
            System.out.println("\n" + ioRete.caricaRete(reteDaVisualizzare).toString());
        }
    }

}
