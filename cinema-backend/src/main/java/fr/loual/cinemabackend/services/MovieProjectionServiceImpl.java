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
import java.util.stream.Collectors;

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
    public CityDTO addCity(CityDTO cityDTO) throws InvalidArgumentException, AlreadyExistsException {
        if(!dtoAndEntityCheck.checkCityDTO(cityDTO)) throw new InvalidArgumentException(ServicesUtils.exceptionMessage("une ville"));
        City cityRepo = cityRepository.findByName(cityDTO.getName());
        if(cityRepo != null) throw new AlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("La ville"));
        log.info("--Enregistrement d'une nouvelle ville...-");
        City city = mapper.DTOToCity(cityDTO);
        cityRepository.save(city);
        log.info(ServicesUtils.infoMessage("La ville", city.getName()));
        return mapper.cityToDTO(city);
    }

    @Override
    public CinemaDTO addCinema(CinemaDTO cinemaDTO) throws InvalidArgumentException, AlreadyExistsException {
        if(!dtoAndEntityCheck.checkCinemaDTO(cinemaDTO)) throw new InvalidArgumentException(ServicesUtils.exceptionMessage("un cinéma"));
        Cinema cinemaRepo = cinemaRepository.findByAltitudeAndLatitudeAndLongitude(cinemaDTO.getAltitude(), cinemaDTO.getLatitude(), cinemaDTO.getLongitude());
        if(cinemaRepo != null) throw new AlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("Le cinéma"));
        log.info("--Enregistrement d'un nouveau cinéma...--");
        Cinema cinema = mapper.DTOToCinema(cinemaDTO);
        cinema.setId(UUID.randomUUID().toString());
        cinemaRepository.save(cinema);
        log.info(ServicesUtils.infoMessage("Le cinéma", cinema.getName()));
        return mapper.cinemaToDTO(cinema);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws InvalidArgumentException, AlreadyExistsException {
        if(!dtoAndEntityCheck.checkCategoryDTO(categoryDTO)) throw new InvalidArgumentException(ServicesUtils.exceptionMessage("une catégorie"));
        Category categoryRepo = categoryRepository.findByName(categoryDTO.getName());
        if(categoryRepo != null) throw new AlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("La catégorie"));
        log.info("--Enregistrement d'une nouvelle catégorie...--");
        Category category = mapper.DTOToCategory(categoryDTO);
        categoryRepository.save(category);
        log.info(ServicesUtils.infoMessage("La catégorie", category.getName()));
        return mapper.categoryToDTO(category);
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) throws InvalidArgumentException, AlreadyExistsException {
        if(!dtoAndEntityCheck.checkMovieDTO(movieDTO)) throw new InvalidArgumentException(ServicesUtils.exceptionMessage("un film"));
        Movie movieRepo = movieRepository.findByTitleAndMakerAndReleaseDate(movieDTO.getTitle(), movieDTO.getMaker(), movieDTO.getReleaseate());
        if (movieRepo != null) throw new AlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("Le film"));
        log.info("--Enregistrement d'un nouveau film...--");
        Movie movie = mapper.DTOToMovie(movieDTO);
        movie.setId(UUID.randomUUID().toString());
        movieRepository.save(movie);
        log.info(ServicesUtils.infoMessage("Le film", movie.getTitle()));
        return mapper.movieToDTO(movie);
    }

    @Override
    public RoomDTO addRoomToCinema(RoomDTO roomDTO) throws InvalidArgumentException, NotFoundException, AlreadyExistsException {
        if(!dtoAndEntityCheck.checkRoomDTO(roomDTO)) throw new InvalidArgumentException(ServicesUtils.exceptionMessage("une salle"));
        Cinema cine = cinemaRepository.findByAltitudeAndLatitudeAndLongitude(roomDTO.getCinema().getAltitude(),
                roomDTO.getCinema().getLatitude(), roomDTO.getCinema().getLongitude());
        if(cine == null) throw new NotFoundException(ServicesUtils.notFoundMessage("Le cinéma"));
        Room roomRepo = roomRepository.findByNameAndCinema(roomDTO.getName(), mapper.DTOToCinema(roomDTO.getCinema()));
        if (roomRepo != null) throw new AlreadyExistsException(ServicesUtils.alreadyExistsErrorMessage("La salle"));
        log.info("--Enregistrement d'une nouvelle salle...--");
        Room room = mapper.DTOToRoom(roomDTO);
        room.setId(UUID.randomUUID().toString());
        Cinema cinema = room.getCinema();
        cinema.getRooms().add(room);
        roomRepository.save(room);
        cinemaRepository.save(cinema);
        log.info(ServicesUtils.infoMessage("La salle", room.getName()));
        return mapper.roomToDTO(room);
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
    public List<CityDTO> getCities() throws NotFoundException {
        List<City> cityList = cityRepository.findAll();
        if (cityList.isEmpty()) throw new NotFoundException(ServicesUtils.notFoundMessage("Les villes"));

        return cityList.stream().map(city -> mapper.cityToDTO(city)).collect(Collectors.toList());
    }

    @Override
    public List<CinemaDTO> getCinemaByCity(CityDTO city) throws NotFoundException {
        City city1 = cityRepository.findByName(city.getName());
        if(city1 == null) throw new NotFoundException(ServicesUtils.notFoundMessage("La ville"));
        List<Cinema> listCin = cinemaRepository.findByCity(city1);
        if(listCin.isEmpty()) throw new NotFoundException(ServicesUtils.notFoundMessage("Les cinémas"));

        return listCin.stream().map(cinema -> mapper.cinemaToDTO(cinema)).collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomByCinema(CinemaDTO cinema) throws NotFoundException {
        Cinema cinema1 = cinemaRepository.findById(cinema.getId()).orElse(null);
        if(cinema1 == null) throw new NotFoundException(ServicesUtils.notFoundMessage("Le cinéma"));
        List<Room> listRoom = roomRepository.findByCinema(cinema1);
        if(listRoom.isEmpty()) throw new NotFoundException(ServicesUtils.notFoundMessage("Les salles"));

        return listRoom.stream().map(room -> mapper.roomToDTO(room)).collect(Collectors.toList());
    }


    @Override
    public List<SeanceDTO> getSeanceForMovie(MovieDTO movie,CinemaDTO cinema, Date date) throws NotFoundException {
        Movie movie1 = movieRepository.findById(movie.getId()).orElse(null);
        Cinema cinema1 = cinemaRepository.findById(cinema.getId()).orElse(null);
        if (movie1 == null || cinema1 == null) throw new NotFoundException(ServicesUtils.notFoundMessage("Le cinéma ou le film"));
        List<Seance> listSeance = projectionRepository.findByMovieAndRoomCinema_IdAndBeginDateIsGreaterThan(movie1,cinema.getId(), date);
        return listSeance.stream().map(seance -> mapper.seanceToDTO(seance)).collect(Collectors.toList());
    }

}
