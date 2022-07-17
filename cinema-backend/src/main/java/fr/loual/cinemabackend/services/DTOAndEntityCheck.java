package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;

public interface DTOAndEntityCheck {

    boolean checkCityDTO(CityDTO city);
    boolean checkCinemaDTO(CinemaDTO cinemaDTO);
    boolean checkCategoryDTO(CategoryDTO categoryDTO);
    boolean checkMovieDTO(MovieDTO movieDTO);
    boolean checkRoomDTO(RoomDTO roomDTO);
    boolean verify(Object[] obj);
}
