package menu;

import model.Rete;
import model.Transizione;
import modelPN.RetePN;
import utils.InputDati;

import java.util.ArrayList;
import java.util.List;

public class MenuEvoluzioneRetePN {
    RetePN reteDaEvolvere;


    MenuEvoluzioneRetePN(Rete reteDaEvolvere){
        this.reteDaEvolvere = (RetePN) reteDaEvolvere;
    }
    public void loopEvoluzione() {
        ArrayList<Transizione> listaTransizioni;
        System.out.println("Inizio evoluzione della rete" + reteDaEvolvere.getID() + ":" );
        System.out.println("\nMarcatura iniziale: \n"+ reteDaEvolvere.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listaTransizioni = (ArrayList<Transizione>) reteDaEvolvere.transizioniAttive();
        while(!listaTransizioni.isEmpty()){
            //Qui dentro vuol dire che ho almeno una transizione abilitata faccio scegliere o no all'utente
            // NB: c'è anche il caso in cui non ci sono transizioni abilitate -> messaggio di sistema e termina simulazione
            //1. Se c'è solo una transizione la scatto e mostro la marcatura
            Transizione daScattare;
            if(listaTransizioni.size() == 1){
                System.out.println("Scatta automaticamente l'unica transizione abilitata "+ listaTransizioni.get(0).getId());
                daScattare = listaTransizioni.get(0);
            }else{
                System.out.println("\nTransizioni attive: ");
                for (int i=0; i<listaTransizioni.size(); i++)
                {
                    System.out.println( (i) + "\t" + listaTransizioni.get(i));
                }
                int scelta = InputDati.leggiIntero("Digitare il numero della transizione che si vuole far scattare >",
                        0, listaTransizioni.size()-1);
                daScattare = listaTransizioni.get(scelta);
                System.out.println("Scatta la transizione "+ daScattare.getId());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reteDaEvolvere.scattaTransizione(daScattare);
            System.out.println("\nMarcatura corrente: \n"+ reteDaEvolvere.toString());

            boolean continuare = InputDati.yesOrNo("Si desidera continuare nell'evoluzione? ");
            if(!continuare) {
                System.out.println("Evoluzione terminata.");
                return; //termina subito il metodo
            }

            listaTransizioni = (ArrayList<Transizione>)reteDaEvolvere.transizioniAttive();
        }
        //Qui mostro all'utente che l'evoluzione è finita e non ci sono transizioni abilitate
        System.out.println("Nessuna transizione abilitata");
        System.out.println("Evoluzione terminata.");
    }
}
