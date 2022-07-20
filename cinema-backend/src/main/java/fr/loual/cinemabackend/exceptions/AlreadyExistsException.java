package fr.loual.cinemabackend.exceptions;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
