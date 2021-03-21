package it.unibs.inge.IS.ProgettoIS;

public class GestoreRete {
    private String MESSAGGIO_CREAZIONE_ARCO = "Inserire ";
    public void elabora(int scelta){
        // non faccio nulla
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
    public void creaRete(){
        String nuovoArco = InputDati.leggiStringaNonVuota(MESSAGGIO_CREAZIONE_ARCO);
    }
    public void visualizzaRete(){

    }
}
