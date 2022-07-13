package fr.loual.cinemabackend.exceptions;

public class CategoryAlreadyExistsException extends Exception {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
