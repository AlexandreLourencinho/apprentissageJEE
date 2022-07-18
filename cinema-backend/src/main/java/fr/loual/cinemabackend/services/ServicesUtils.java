package fr.loual.cinemabackend.services;

public class ServicesUtils {

    public static String exceptionMessage(String message) {
        return String.format("Les champs nécessaires à l'ajout d'%s n'ont pas été remplis.", message);
    }

    public static String alreadyExistsErrorMessage(String message) {
        return String.format("%s que vous avez essayé d'ajouter est déjà répertorié.", message);
    }

    public static String infoMessage(String type, String name) {
        return String.format("-- %s %s à bien été enregistré --",type, name);
    }

    public static String notFoundMessage(String message) {
        return String.format("%s n'existe pas ou n'a pas encore été ajouté.", message);
    }

}
