package fr.loual.cinemabackend.exceptions;

public class InvalidMovieArgumentException extends Exception {
    public InvalidMovieArgumentException(String message) {
        super(message);
    }
}
