package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;
import fr.loual.cinemabackend.exceptions.*;

import java.util.Date;
import java.util.List;

public interface MovieProjectionService {

    CityDTO addCity(CityDTO cityDTO) throws InvalidCityArgumentException, CityAlreadyRegisteredException;
    CinemaDTO addCinema(CinemaDTO cinemaDTO) throws InvalidCinemaArgumentException, CinemaAlreadyExistsException;
    CategoryDTO addCategory(CategoryDTO categoryDTO) throws InvalidCategoryArgumentException, CategoryAlreadyExistsException;
    MovieDTO addMovie(MovieDTO movieDTO) throws InvalidMovieArgumentException, MovieAlreadyExistsException;
    RoomDTO addRoomToCinema(RoomDTO roomDTO) throws InvalidRoomArgumentException, RoomAlreadyExistsException, CinemaNotFoundException;
    PlaceDTO addPlaceToRoom(PlaceDTO placeDTO);
    TicketDTO addTicket(TicketDTO ticketDTO);
    SeanceDTO addSeance(SeanceDTO seanceDTO);

    List<CityDTO> getCities();
    List<CinemaDTO> getCinemaByCity(CityDTO cityDTO);
    List<SeanceDTO> getSeanceForMovie(MovieDTO movieDTO, Date date);


}
