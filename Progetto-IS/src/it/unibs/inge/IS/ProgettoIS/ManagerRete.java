package it.unibs.inge.IS.ProgettoIS;

import java.util.List;

public class ManagerRete {
    private IORete ioRete;

    private String MESSAGGIO_CREAZIONE_ARCO = "Inserire una coppia POSTO-TRANSIZIONE o TRANSIZIONE-POSTO:";
    public ManagerRete(IORete ioRete){
        this.ioRete = ioRete;
    }


    public void elabora(int scelta){
        switch (scelta) {
            case 1:
                creaRete();
                break;
            case 2:
                visualizzaRete();
                break;
            default:
                ;
                break;
        }
    }
    //TODO: Problema come so cosa l'utente sta inserendo? se un posto-transizione o una transizione-posto?
    public void creaRete(){
        Rete nuovaRete = new Rete();
        String nuovoArco = InputDati.leggiStringaNonVuota(MESSAGGIO_CREAZIONE_ARCO);
    }
    public void visualizzaRete(){
        int retiSalvate = ioRete.numeroRetiSalvate();
        if(retiSalvate == 0){
            System.out.println("Non ci sono reti salvate al momento");
        }
        else {
            List<String> listaReti = ioRete.getNomiRetiSalvate();
            for (int i=0; i<listaReti.size(); i++)
            {
                System.out.println( (i) + "\t" + listaReti.get(i));
            }
           int scelta = InputDati.leggiIntero("Digitare il numero della rete da visualizzare >",
                   0, retiSalvate);
        }


    }
}
