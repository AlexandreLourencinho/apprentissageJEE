package fr.loual.cinemabackend.security.exceptions;

public class RoleNotFoundException extends Exception{

    public RoleNotFoundException(String message) {
        super(message);
    }
}
