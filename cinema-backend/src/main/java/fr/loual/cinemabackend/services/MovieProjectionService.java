package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;

import java.util.Date;
import java.util.List;

public interface MovieProjectionService {

    CityDTO addCity(CityDTO city);
    CinemaDTO addCinema(CinemaDTO cinema);
    CategoryDTO addCategory(CategoryDTO category);
    MovieDTO addMovie(MovieDTO movie);
    RoomDTO addRoomToCinema(RoomDTO room);
    PlaceDTO addPlaceToRoom(PlaceDTO place);
    TicketDTO addTicket(TicketDTO ticket);
    SeanceDTO addSeance(SeanceDTO seance);

    List<CityDTO> getCities();
    List<CinemaDTO> getCinemaByCity(CityDTO city);
    List<SeanceDTO> getSeanceForMovie(MovieDTO movie, Date date);


}
