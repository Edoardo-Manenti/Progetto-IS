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
                    new String[]{"Crea nuova rete", "Visualizza reti salvate"});

    public static void creaReteN(){
        //Attenzione ricordati di gestire i nomi uguali
        String nomeRete = InputDati.leggiStringaNonVuota("Inserire il nome della rete: ");
        new MenuCreaReteN(new Rete(nomeRete)).loopCreaReteN();

    }


    public static void visualizzaRete(){
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
            default:
                ;
                break;
        }
    }

}
