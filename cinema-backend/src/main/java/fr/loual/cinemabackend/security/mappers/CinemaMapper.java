package fr.loual.cinemabackend.security.mappers;

import fr.loual.cinemabackend.dtos.CategoryDTO;
import fr.loual.cinemabackend.dtos.CinemaDTO;
import fr.loual.cinemabackend.dtos.CityDTO;
import fr.loual.cinemabackend.entities.Category;
import fr.loual.cinemabackend.entities.Cinema;
import fr.loual.cinemabackend.entities.City;
import org.mapstruct.Mapper;

@Mapper
public interface CinemaMapper {

    City cityDTOToCity(CityDTO cityDTO);
    CityDTO cityToCityDTO(City city);
    Cinema cinemaDTOToCinema(CinemaDTO cinemaDTO);
    CinemaDTO cinemaToCinemaDTO(Cinema cinema);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
    CategoryDTO categoryToCategoryDTO(Category category);

}
