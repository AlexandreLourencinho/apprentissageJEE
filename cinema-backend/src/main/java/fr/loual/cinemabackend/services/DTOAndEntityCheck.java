package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.CategoryDTO;
import fr.loual.cinemabackend.dtos.CinemaDTO;
import fr.loual.cinemabackend.dtos.CityDTO;
import fr.loual.cinemabackend.dtos.MovieDTO;

public interface DTOAndEntityCheck {

    boolean checkCityDTO(CityDTO city);
    boolean checkCinemaDTO(CinemaDTO cinemaDTO);
    boolean checkCategoryDTO(CategoryDTO categoryDTO);
    boolean checkMovieDTO(MovieDTO movieDTO);
    boolean verify(Object[] obj);
}
