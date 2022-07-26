package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;
import fr.loual.cinemabackend.exceptions.*;

import java.util.Date;
import java.util.List;

public interface MovieProjectionService {

    CityDTO addCity(CityDTO cityDTO) throws InvalidArgumentException, AlreadyExistsException;
    CinemaDTO addCinema(CinemaDTO cinemaDTO) throws InvalidArgumentException, AlreadyExistsException;
    CategoryDTO addCategory(CategoryDTO categoryDTO) throws AlreadyExistsException, InvalidArgumentException;
    MovieDTO addMovie(MovieDTO movieDTO) throws InvalidArgumentException, AlreadyExistsException;
    RoomDTO addRoomToCinema(RoomDTO roomDTO) throws InvalidArgumentException, NotFoundException, AlreadyExistsException;
    PlaceDTO addPlaceToRoom(PlaceDTO placeDTO);
    TicketDTO addTicket(TicketDTO ticketDTO);
    SeanceDTO addSeance(SeanceDTO seanceDTO);

    List<CityDTO> getCities() throws NotFoundException;
    List<CinemaDTO> getCinemaByCity(CityDTO cityDTO) throws NotFoundException;
    List<RoomDTO> getRoomByCinema(CinemaDTO cinema) throws NotFoundException;
    List<SeanceDTO> getSeanceForMovie(MovieDTO movieDTO,CinemaDTO cinema, Date date) throws NotFoundException;


}
