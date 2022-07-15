package fr.loual.cinemabackend.exceptions;

public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
