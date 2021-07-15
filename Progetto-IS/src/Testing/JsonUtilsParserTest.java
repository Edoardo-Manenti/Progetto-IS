package Testing;

import model.*;
import utils.IORete;
import utils.JsonUtilsParser;
import utils.PetriNetException;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsParserTest {
    private String jsonFile;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // Come precondizione di ogni test la rete portante su cui si basa la
        // RetePN che vogliamo testare deve esistere salvata in locale
        String id = "retePortante";
        Rete retePortante = new Rete(id);
        Posto p1 = new Posto("p1");
        Posto p2 = new Posto("p2");
        Posto p3 = new Posto("p3");
        Transizione t1 = new Transizione("t1");
        Transizione t2 = new Transizione("t2");
        retePortante.aggiungiArco(new Arco(p1,t1));
        retePortante.aggiungiArco(new Arco(t1,p2));
        retePortante.aggiungiArco(new Arco(t2, p3));
        retePortante.aggiungiArco(new Arco(p1,t2));
        try {
            IORete.getInstance().salvaRete(retePortante);
        }
        catch(Exception e) {
           System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void parsaJsonRetePortanteNonTrovata() {
        jsonFile ="{\"retePortante\":\"NonEsisto.json\",\"marcatura\":[{\"n_token\":3,\"nodo\":\"p1\"},{\"n_token\":0,\"nodo\":\"p2\"},{\"n_token\":0,\"nodo\":\"p3\"}],\"object\":\"RETEPN\",\"pesi\":[{\"arco\":\"t1 -> p2\",\"peso\":1},{\"arco\":\"t2 -> p3\",\"peso\":1},{\"arco\":\"p1 -> t1\",\"peso\":3}, {\"arco\":\"p1 -> t2\",\"peso\":2}]}";
        PetriNetException exc = assertThrows(PetriNetException.class, () -> JsonUtilsParser.parsaJson(jsonFile));
        assertTrue(exc.getMessage().contains("rete portante non trovata"));
    }

    @org.junit.jupiter.api.Test
    void parsaJsonReteArchiPesiNegatvi() {
        // PESO NEGATIVO ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------>|
        jsonFile ="{\"retePortante\":\"retePortante.json\",\"marcatura\":[{\"n_token\":3,\"nodo\":\"p1\"},{\"n_token\":0,\"nodo\":\"p2\"},{\"n_token\":0,\"nodo\":\"p3\"}],\"object\":\"RETEPN\",\"pesi\":[{\"arco\":\"t1 -> p2\",\"peso\":-3},{\"arco\":\"t2 -> p3\",\"peso\":1},{\"arco\":\"p1 -> t1\",\"peso\":3}, {\"arco\":\"p1 -> t2\",\"peso\":2}]}";
        PetriNetException exc = assertThrows(PetriNetException.class, () -> JsonUtilsParser.parsaJson(jsonFile));
        assertTrue(exc.getMessage().contains("archi con pesi negativi"));
    }

    @org.junit.jupiter.api.Test
    void parsaJsonReteMarcaturaNegativa() {
        // MARCATURA NEGATIVA ------------------------------------------------------> |
        jsonFile ="{\"retePortante\":\"retePortante.json\",\"marcatura\":[{\"n_token\":-1,\"nodo\":\"p1\"},{\"n_token\":0,\"nodo\":\"p2\"},{\"n_token\":0,\"nodo\":\"p3\"}],\"object\":\"RETEPN\",\"pesi\":[{\"arco\":\"t1 -> p2\",\"peso\":1},{\"arco\":\"t2 -> p3\",\"peso\":1},{\"arco\":\"p1 -> t1\",\"peso\":3}, {\"arco\":\"p1 -> t2\",\"peso\":2}]}";
        PetriNetException exc = assertThrows(PetriNetException.class, () -> JsonUtilsParser.parsaJson(jsonFile));
        assertTrue(exc.getMessage().contains("marcatura sintatticamente sbagliata"));
    }

}