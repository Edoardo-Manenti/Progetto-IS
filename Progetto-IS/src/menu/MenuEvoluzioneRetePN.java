package menu;

import model.Rete;
import modelPN.RetePN;

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
            //1. Se c'è solo una transizione lo dico e mostro l'evoluzione
            //2, Se ce ne sono di più applico il solito pattern per fare vedere all'utente una lista di alternative e operare una scelta
            //vedasi visualizzaRetePN nel MainMenu
        }
        //Qui mostro all'utente che l'evoluzione è finita.
        System.out.println("Evoluzione terminata.");
    }
}
