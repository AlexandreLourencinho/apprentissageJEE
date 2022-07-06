package fr.loual.cinemabackend.security.exceptions;

public class NullUserRequiredFieldException extends Exception{

    public NullUserRequiredFieldException(String message) {
        super(message);
    }

}
