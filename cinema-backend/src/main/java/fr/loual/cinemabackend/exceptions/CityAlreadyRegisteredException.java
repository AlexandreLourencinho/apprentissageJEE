package fr.loual.cinemabackend.exceptions;

public class CityAlreadyRegisteredException extends Exception {
    public CityAlreadyRegisteredException(String message) {
        super(message);
    }
}
