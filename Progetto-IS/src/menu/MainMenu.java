package menu;

import model.Rete;
import modelPN.RetePN;
import utils.IORete;
import utils.InputDati;

import java.util.List;

public class MainMenu {

    private static final IORete ioRete = new IORete();

    private static final Menu mainMenu =
            new Menu("Buongiorno, questo software permette tramite interfaccia testuale di inserire, " +
                                                  "visualizzare e modificare delle reti di Petri. " +
                                                  "\n Cosa desidera fare?",
                    new String[]{"Crea nuova rete N", "Visualizza reti N salvate", "Visualizza reti PN salvate", "Visualizza reti PNP salvate", "Crea nuova rete PN", "Mostra evoluzione di una rete PN", "Crea nuova rete PNP", "Mostra evoluzione di una rete PNP"});

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
        new MenuCreaReteN(new Rete(nomeRete)).loopCreaReteN();


    }

    private static void creaRetePN() {
        //Qui faccio selezionare all'utente la rete N di riferimento e la passo a MenuCreaRetePN

        List<String> listaReti = ioRete.getNomiRetiN();
        for (int i=0; i<listaReti.size(); i++)
        {
            System.out.println( (i) + "\t" + listaReti.get(i));
        }
        int scelta = InputDati.leggiIntero("Digitare il numero della rete da selezionare come Rete N di riferimento >",
                0, listaReti.size() -1);
        String reteSelezionata = listaReti.get(scelta);
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        new MenuCreaRetePN(ioRete.caricaRete(reteSelezionata), reteSelezionata).loopCreaRetePN();
    }
    private static void creaRetePNP() {
        List<String> listaReti = ioRete.getNomiRetiPN();
        for (int i=0; i<listaReti.size(); i++)
        {
            System.out.println( (i) + "\t" + listaReti.get(i));
        }
        int scelta = InputDati.leggiIntero("Digitare il numero della rete da selezionare come Rete PN di riferimento >",
                0, listaReti.size() -1);
        String reteSelezionata = listaReti.get(scelta);
        System.out.println("Hai selezionato la rete "+ reteSelezionata);

        new MenuCreaRetePNP((RetePN) ioRete.caricaRete(reteSelezionata), reteSelezionata).loopCreaRetePNP();
    }



    //TODO: Discriminare fra N, PN e PNP -> Usare metodo intermedio
    public static void visualizzaReteN(){
        int nrretiSalvate = ioRete.getNomiRetiN().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiN();
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
            default:
                ;
                break;
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
            for (int i=0; i<listaReti.size(); i++)
            {
                System.out.println( (i) + "\t" + listaReti.get(i));
            }
            int scelta = InputDati.leggiIntero("Digitare il numero della rete per cui mostrare l'evoluzione >",
                    0, nrretiSalvate-1);
            String reteDaEvolvere = listaReti.get(scelta);
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
            for (int i=0; i<listaReti.size(); i++)
            {
                System.out.println( (i) + "\t" + listaReti.get(i));
            }
            int scelta = InputDati.leggiIntero("Digitare il numero della rete per cui mostrare l'evoluzione >",
                    0, nrretiSalvate-1);
            String reteDaEvolvere = listaReti.get(scelta);
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

    //TODO: Check con nuovo metodo di Edo
    private static void visualizzaRetiPNP() {
        int nrretiSalvate = ioRete.getNomiRetiPNP().size();
        if(nrretiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiPNP();
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

}
