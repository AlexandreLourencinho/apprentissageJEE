package fr.loual.cinemabackend.mappers;

import fr.loual.cinemabackend.dtos.*;
import fr.loual.cinemabackend.entities.*;
import org.mapstruct.Mapper;

@Mapper
public interface CinemaMapper {

    City cityDTOToCity(CityDTO cityDTO);
    CityDTO cityToCityDTO(City city);
    Cinema cinemaDTOToCinema(CinemaDTO cinemaDTO);
    CinemaDTO cinemaToCinemaDTO(Cinema cinema);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
    CategoryDTO categoryToCategoryDTO(Category category);
    Movie movieDTOToMovie(MovieDTO movieDTO);
    MovieDTO movieToMovieDTO(Movie movie);
    Room roomDTOToRoom(RoomDTO roomDTO);
    RoomDTO roomToRoomDTO(Room room);

}
