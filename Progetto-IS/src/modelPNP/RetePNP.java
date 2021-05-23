package modelPNP;

public class RetePNP extends RetePN{
    HashMap<Transizione, Integer> priorità;
    //TODO: costruttore del tipo : RetePNP( RetePN rete)

    public boolean setPriorità(HashMap<Transizione, Integer> priorità) {
        this.priorità = priorità;
    }
}
