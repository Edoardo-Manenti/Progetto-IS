package utils;

//Commento pattern Convert Exception: abbiamo creato una
// nostra personale eccezione che incapsula le eccezioni nella lettura del file json e di conseguenza le "rimanda" verso l'alto.
public class PetriNetException extends Exception {
    public PetriNetException(String message){
        super(message);
    }
}