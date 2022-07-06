package fr.loual.cinemabackend.security.exceptions;

public class NullRoleRequiredFieldException extends Exception{

    public NullRoleRequiredFieldException(String message) {
        super(message);
    }
}
