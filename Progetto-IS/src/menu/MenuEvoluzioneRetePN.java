package menu;

import model.Rete;
import modelPN.RetePN;
import utils.InputDati;

public class MenuEvoluzioneRetePN {
    RetePN reteDaEvolvere;

    MenuEvoluzioneRetePN(Rete reteDaEvolvere){
        this.reteDaEvolvere = (RetePN) reteDaEvolvere;
    }
    public void loopEvoluzione() {
        System.out.println("Inizio evoluzione della rete" + reteDaEvolvere.getID() + ":" );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(reteDaEvolvere.hasTransizioniAbilitate()){
            //Qui dentro vuol dire che ho almeno una transizione abilitata faccio scegliere o no all'utente
            //1. Se c'è solo una transizione la scatto e mostro la marcatura
            //2, Se ce ne sono di più applico il solito pattern per fare vedere all'utente una lista di alternative e operare una scelta
            //vedasi visualizzaRetePN nel MainMenu

            //Dopo i due casi esclusivi 1. e 2. chiedo all'utente se vuole continuare

            boolean continuare = InputDati.yesOrNo("Si desidera continuare nell'evoluzione? ");
            if(!continuare) {
                System.out.println("Evoluzione terminata.");
                return; //termina subito il metodo
            }
        }
        //Qui mostro all'utente che l'evoluzione è finita.
        System.out.println("Evoluzione terminata.");
    }
}
