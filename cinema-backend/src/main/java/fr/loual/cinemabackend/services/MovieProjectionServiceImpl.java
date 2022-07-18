package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;
import fr.loual.cinemabackend.entities.*;
import fr.loual.cinemabackend.exceptions.*;
import fr.loual.cinemabackend.repositories.*;
import fr.loual.cinemabackend.mappers.CinemaMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class MovieProjectionServiceImpl implements MovieProjectionService {

    private CategoryRepository categoryRepository;
    private CinemaRepository cinemaRepository;
    private CityRepository cityRepository;
    private MovieProjectionRepository projectionRepository;
    private MovieRepository movieRepository;
    private PlaceRepository placeRepository;
    private RoomRepository roomRepository;
    private TicketRepository ticketRepository;
    private DTOAndEntityCheck dtoAndEntityCheck;
    private CinemaMapper mapper;

    // TODO externaliser? code qui se répète. + refléchir aux dtos, collections?
    // TODO 3 erreurs génériques (potentiellement + par la suite) : InvalidArgument, AlreadyExists,NotFound. Le message d'erreur devrait suffire pour savoir ce qui existe déjà ou non.
    // permettra de ne pas avoir 50 Exceptions qui au final fond la même chose

    @Override
    public CityDTO addCity(CityDTO cityDTO) throws InvalidCityArgumentException, CityAlreadyExistsException {
        if(!dtoAndEntityCheck.checkCityDTO(cityDTO)) throw new InvalidCityArgumentException(ServicesUtils.exceptionMessage("une ville"));
        City cityRepo = cityRepository.findByName(cityDTO.getName());
        if(cityRepo != null) throw new CityAlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("La ville"));
        log.info("--Enregistrement d'une nouvelle ville...-");
        City city = mapper.cityDTOToCity(cityDTO);
        cityRepository.save(city);
        log.info(ServicesUtils.infoMessage("La ville", city.getName()));
        return mapper.cityToCityDTO(city);
    }

    @Override
    public CinemaDTO addCinema(CinemaDTO cinemaDTO) throws InvalidCinemaArgumentException, CinemaAlreadyExistsException {
        if(!dtoAndEntityCheck.checkCinemaDTO(cinemaDTO)) throw new InvalidCinemaArgumentException(ServicesUtils.exceptionMessage("un cinéma"));
        Cinema cinemaRepo = cinemaRepository.findByAltitudeAndLatitudeAndLongitude(cinemaDTO.getAltitude(), cinemaDTO.getLatitude(), cinemaDTO.getLongitude());
        if(cinemaRepo != null) throw new CinemaAlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("Le cinéma"));
        log.info("--Enregistrement d'un nouveau cinéma...--");
        Cinema cinema = mapper.cinemaDTOToCinema(cinemaDTO);
        cinema.setId(UUID.randomUUID().toString());
        cinemaRepository.save(cinema);
        log.info(ServicesUtils.infoMessage("Le cinéma", cinema.getName()));
        return mapper.cinemaToCinemaDTO(cinema);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws InvalidCategoryArgumentException, CategoryAlreadyExistsException {
        if(!dtoAndEntityCheck.checkCategoryDTO(categoryDTO)) throw new InvalidCategoryArgumentException(ServicesUtils.exceptionMessage("une catégorie"));
        Category categoryRepo = categoryRepository.findByName(categoryDTO.getName());
        if(categoryRepo != null) throw new CategoryAlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("La catégorie"));
        log.info("--Enregistrement d'une nouvelle catégorie...--");
        Category category = mapper.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        log.info(ServicesUtils.infoMessage("La catégorie", category.getName()));
        return mapper.categoryToCategoryDTO(category);
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) throws InvalidMovieArgumentException, MovieAlreadyExistsException {
        if(!dtoAndEntityCheck.checkMovieDTO(movieDTO)) throw new InvalidMovieArgumentException(ServicesUtils.exceptionMessage("un film"));
        Movie movieRepo = movieRepository.findByTitleAndMakerAndReleaseDate(movieDTO.getTitle(), movieDTO.getMaker(), movieDTO.getReleaseate());
        if (movieRepo != null) throw new MovieAlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("Le film"));
        log.info("--Enregistrement d'un nouveau film...--");
        Movie movie = mapper.movieDTOToMovie(movieDTO);
        movie.setId(UUID.randomUUID().toString());
        movieRepository.save(movie);
        log.info(ServicesUtils.infoMessage("Le film", movie.getTitle()));
        return mapper.movieToMovieDTO(movie);
    }

    @Override
    public RoomDTO addRoomToCinema(RoomDTO roomDTO) throws InvalidRoomArgumentException, RoomAlreadyExistsException, CinemaNotFoundException {
        if(!dtoAndEntityCheck.checkRoomDTO(roomDTO)) throw new InvalidRoomArgumentException(ServicesUtils.exceptionMessage("une salle"));
        Cinema cine = cinemaRepository.findByAltitudeAndLatitudeAndLongitude(roomDTO.getCinema().getAltitude(),
                roomDTO.getCinema().getLatitude(), roomDTO.getCinema().getLongitude());
        if(cine == null) throw new CinemaNotFoundException(ServicesUtils.notFoundMessage("Le cinéma"));
        Room roomRepo = roomRepository.findByNameAndCinema(roomDTO.getName(), mapper.cinemaDTOToCinema(roomDTO.getCinema()));
        if (roomRepo != null) throw new RoomAlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("La salle"));
        log.info("--Enregistrement d'une nouvelle salle...--");
        Room room = mapper.roomDTOToRoom(roomDTO);
        room.setId(UUID.randomUUID().toString());
        Cinema cinema = room.getCinema();
        cinema.getRooms().add(room);
        roomRepository.save(room);
        cinemaRepository.save(cinema);
        log.info(ServicesUtils.infoMessage("La salle", room.getName()));
        return mapper.roomToRoomDTO(room);
    }

    @Override
    public PlaceDTO addPlaceToRoom(PlaceDTO place) {
        return null;
    }

    @Override
    public TicketDTO addTicket(TicketDTO ticket) {
        return null;
    }

    @Override
    public SeanceDTO addSeance(SeanceDTO seance) {
        return null;
    }

    @Override
    public List<CityDTO> getCities() {
        return null;
    }

    @Override
    public List<CinemaDTO> getCinemaByCity(CityDTO city) {
        return null;
    }

    @Override
    public List<SeanceDTO> getSeanceForMovie(MovieDTO movie, Date date) {
        return null;
    }

}
