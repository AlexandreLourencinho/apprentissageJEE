package fr.loual.cinemabackend.mappers;

import fr.loual.cinemabackend.dtos.*;
import fr.loual.cinemabackend.entities.*;
import org.mapstruct.Mapper;

@Mapper
public interface CinemaMapper {

    City DTOToCity(CityDTO cityDTO);
    CityDTO cityToDTO(City city);
    Cinema DTOToCinema(CinemaDTO cinemaDTO);
    CinemaDTO cinemaToDTO(Cinema cinema);
    Category DTOToCategory(CategoryDTO categoryDTO);
    CategoryDTO categoryToDTO(Category category);
    Movie DTOToMovie(MovieDTO movieDTO);
    MovieDTO movieToDTO(Movie movie);
    Room DTOToRoom(RoomDTO roomDTO);
    RoomDTO roomToDTO(Room room);
    Seance DTOToSeance(SeanceDTO seanceDTO);
    SeanceDTO seanceToDTO(Seance seance);

}
