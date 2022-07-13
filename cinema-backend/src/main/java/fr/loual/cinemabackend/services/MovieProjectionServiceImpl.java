package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;
import fr.loual.cinemabackend.entities.Category;
import fr.loual.cinemabackend.entities.Cinema;
import fr.loual.cinemabackend.entities.City;
import fr.loual.cinemabackend.exceptions.*;
import fr.loual.cinemabackend.repositories.*;
import fr.loual.cinemabackend.security.mappers.CinemaMapper;
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

    @Override
    public CityDTO addCity(CityDTO cityDTO) throws InvalidCityArgumentException, CityAlreadyRegisteredException {
        if(!dtoAndEntityCheck.checkCityDTO(cityDTO)) throw new InvalidCityArgumentException("Tous les champs nécessaires à l'ajout d'une ville n'ont pas été remplis.");
        City cityRepo = cityRepository.findByName(cityDTO.getName());
        if(cityRepo != null) throw new CityAlreadyRegisteredException("La ville à déjà été ajoutée.");
        log.info("--Enregistrement d'une nouvelle ville...-");
        City city = mapper.cityDTOToCity(cityDTO);
        cityRepository.save(city);
        log.info("--La ville " + city.getName() + " à bien été enregistrée.--");
        return mapper.cityToCityDTO(city);
    }

    @Override
    public CinemaDTO addCinema(CinemaDTO cinemaDTO) throws InvalidCinemaArgumentException, CinemaAlreadyExistsException {
        if(!dtoAndEntityCheck.checkCinemaDTO(cinemaDTO)) throw new InvalidCinemaArgumentException("Tous les champs nécessaires à l'ajout d'un cinéma n'ont pas été remplis.");
        Cinema cinemaRepo = cinemaRepository.findByAltitudeAndLatitudeAndLongitude(cinemaDTO.getAltitude(), cinemaDTO.getLatitude(), cinemaDTO.getLongitude());
        if(cinemaRepo != null) throw new CinemaAlreadyExistsException("Le cinéma que vous avez essayé d'ajouter est déjà répertorié.");
        log.info("--Enregistrement d'un nouveau cinéma...--");
        Cinema cinema = mapper.cinemaDTOToCinema(cinemaDTO);
        cinema.setId(UUID.randomUUID().toString());
        cinemaRepository.save(cinema);
        log.info("--Le cinéma " + cinema.getName() + " à bien été enregistré--");
        return mapper.cinemaToCinemaDTO(cinema);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) throws InvalidCategoryArgumentException, CategoryAlreadyExistsException {
        if(!dtoAndEntityCheck.checkCategoryDTO(categoryDTO)) throw new InvalidCategoryArgumentException("Les champs nécessaires à l'ajout d'une catégorie n'ont pas été remplis.");
        Category categoryRepo = categoryRepository.findByName(categoryDTO.getName());
        if(categoryRepo != null) throw new CategoryAlreadyExistsException("La catégorie que vous avez essayé d'ajouter est déjà répertoriée.");
        log.info("--Enregistrement d'une nouvelle catégorie...--");
        Category category = mapper.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        log.info("--La catégorie " + category.getName() + " à bien été enregistrée.--");
        return mapper.categoryToCategoryDTO(category);
    }

    @Override
    public MovieDTO addMovie(MovieDTO movie) {
        return null;
    }

    @Override
    public RoomDTO addRoomToCinema(RoomDTO room) {
        return null;
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
