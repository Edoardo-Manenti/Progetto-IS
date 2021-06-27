package modelPNP;

import model.Arco;
import model.Nodo;
import model.Transizione;
import modelPN.RetePN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetePNP extends RetePN {

    HashMap<Transizione, Integer> priority;

    public RetePNP(String id, ArrayList<Arco> archi, HashMap<String, Nodo> nodi) {
        super(id, archi, nodi);
    }
    public RetePNP(RetePN rete){
        super(rete);
        //Setto per tutte le transizioni la priorità di default
        for (Transizione t:rete.getTransizioni())
            priority.put(t, 1);
    }

    public HashMap<Transizione, Integer> getPriority() {
        return priority;
    }
    public void setPriority(HashMap<Transizione, Integer> priority) {
        this.priority = priority;
    }
    public void modificaPriorita(Transizione t, int x){
        if(priority.containsKey(t)) priority.put(t,x);
    }

    @Override
    public List<Transizione> transizioniAttive(){
        List<Transizione> listaAttive = super.transizioniAttive();
        //Scandisco la lista e determino la priorità massima,
        // ritorno la lista delle transizioni con priorità = max
        ArrayList<Transizione> listaDaRitornare = new ArrayList<>();
        int max = 0;
        int prioritaCorrente = 0;
        for (Transizione t :listaAttive) {
            prioritaCorrente = priority.get(t);
            if (prioritaCorrente == max){
                listaDaRitornare.add(t);
            }
            else if(prioritaCorrente > max){
                listaDaRitornare.clear();
                listaDaRitornare.add(t);
                max = prioritaCorrente;
            }
        }
        return listaDaRitornare;

    }

    @Override
    public String getType(){
        return "RetePNP";
    }

    @Override
    public boolean equals(Object o) {
       if(!super.equals(o)) return false;
       else{
            RetePNP rete = (RetePNP) o;
            //Le due liste di transizioni devono essere uguali
            if(!this.priority.keySet().containsAll(rete.priority.keySet())) return false;
           for (Transizione t : this.priority.keySet()) {
               if(!rete.priority.get(t).equals(this.priority.get(t))) return false;//se due transizioni uguali hanno priorità diverse esplode tutto
           }

           //arrivato qui ho fatto tutti i controlli
           return true;
       }
    }
}
