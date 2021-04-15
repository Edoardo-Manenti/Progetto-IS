package menu;

import model.Rete;
import utils.IORete;
import utils.InputDati;

import java.util.List;

public class MainMenu {

    private static final IORete ioRete = new IORete();

    private static final Menu mainMenu =
            new Menu("Buongiorno, questo software permette tramite interfaccia testuale di inserire, " +
                                                  "visualizzare e modificare delle reti di Petri. " +
                                                  "\n Cosa desidera fare?",
                    new String[]{"Crea nuova rete N", "Visualizza reti salvate", "Crea nuova rete PN"});

    public static void creaReteN(){
        //Attenzione ricordati di gestire i nomi uguali
        String nomeRete = InputDati.
                leggiStringaNonVuota("Inserire il nome della rete: ");
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

        new MenuCreaRetePN(ioRete.caricaRete(reteSelezionata)).loopCreaReteN();
    }


    //TODO: Discriminare fra N, PN e PNP -> Usare metodo intermedio
    public static void visualizzaRete(){
        int nrretiSalvate = ioRete.numeroRetiSalvate();
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
                visualizzaRete();
                break;
            case 3:
                creaRetePN();
                break;
            default:
                ;
                break;
        }
    }

}
