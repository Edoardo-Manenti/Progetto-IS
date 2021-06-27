package modelPNP;

import model.Arco;
import model.Nodo;
import model.Rete;
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
        priority = new HashMap<>();
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
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + this.getID()).append("\n\n");
        sb.append("NODI:").append("\n");
        for (Nodo n : this.getNodi().values()) {
            if(n.isTransizione()){
                sb.append(n.toString() + " priorita': " + this.priority.get((Transizione)n)).append("\n");
            }
            else{
                sb.append(n.toString()).append("\n");

            }
        }
        sb.append("\n").append("ARCHI:").append("\n");
        for (Arco a:this.getArchi())
            sb.append(a.toString()).append("\n");

        return sb.toString();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals((Rete)obj);
    }

    public boolean equals(Rete rete) {
       if(!super.equals(rete)) return false;
       else if(rete instanceof RetePNP){
           RetePNP r = (RetePNP) rete;
            //Le due liste di transizioni devono essere uguali
            if(!this.priority.keySet().containsAll(r.priority.keySet())) return false;
           for (Transizione t : this.priority.keySet()) {
               if(!r.priority.get(t).equals(this.priority.get(t))) return false;//se due transizioni uguali hanno priorità diverse esplode tutto
           }
       }
        return true;
    }
}
