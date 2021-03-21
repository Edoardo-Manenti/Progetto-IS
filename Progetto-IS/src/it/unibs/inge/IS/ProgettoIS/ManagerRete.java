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
    
    /*
     * MODELLO 1:
     * 
     * 1) Scelta tra nuova rete o visualizza rete
     * 2.1) se nuova rete:
     * 2.1.1) Crea posto iniziale o transizione iniziale
     * 2.1.2) Scelta tra arco T->P o arco P->T o termina modifiche
     * 2.1.3.1.1) se T->P:
     * 2.1.3.1.2) Indica nomeT e nomeP
     * 2.1.3.1.3) Controllo che almeno uno dei due esista
     * 2.1.3.1.4) se esiste -> creo nodo mancante e aggiungo arco
     * 2.1.3.1.5) se esistono già entrambi e l'arco non esiste, lo aggiungo, altrimenti informo che l'arco già esiste
     * 2.1.3.1.6) Torno a 2.1.2
     * 2.1.3.2.1) se P->T: analogo a T->P
     * 2.1.3.3.1) se termina modifiche:
     * 2.1.3.3.2) scelta tra salvare la rete o scartare le modifiche
     * 2.1.3.3.3) se salva rete : scelgi nome con cui salvare la rete
     * 2.2.1) se visualizza rete: presenta i nomi e chiede di scegliere la rete o torna menu principale
     * 2.2.2) visualizza rete scelta 
     * 
     * 
     * MODELLO 2:
     * 
     * A ogni passo chiedo se si vuole aggiungere un nuovo posto, 
     *        aggiungere una transizione, aggiungere un arco, eliminare nodo, eliminare arco, terminare le modifiche
     * se nuovo posto:
     * - chiedo id
     * - controllo che non esista
     * 
     * se nuova transizione:
     * - chiedo id
     * - controllo che non esista
     * 
     * se nuovo arco:
     * - chiedo origine e destinazione
     * - controllo che esistano ENTRAMBI
     * - controllo che siano di diverso tipo
     * - controllo che l'arco non sia già presente
     * - aggiungo arco
     * 
     * se elimina arco/nodo -> chiedo id ed elimino
     * 
     * se termina modifiche:
     * - controllo che ogni transizione abbia almeno un precedente E almeno un successivo
     * - controllo che ogni posto abbia almeno un precedento O un successivo
     * - altrimenti informo del Nodo che non rispetta tale condizione e ritorno allo stadio di modifica
     * - salvo rete
     * 
     */
    
    
    
    public void visualizzaRete(){
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
                   0, nrretiSalvate);
            String reteDaVisualizzare = listaReti.get(scelta);
            System.out.println(ioRete.caricaRete(reteDaVisualizzare).toString());
        }


    }
}
