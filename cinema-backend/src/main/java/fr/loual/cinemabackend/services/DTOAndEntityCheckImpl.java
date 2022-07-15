package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.CategoryDTO;
import fr.loual.cinemabackend.dtos.CinemaDTO;
import fr.loual.cinemabackend.dtos.CityDTO;

import java.util.Objects;
import java.util.stream.Stream;

public class DTOAndEntityCheckImpl implements DTOAndEntityCheck {

    @Override
    public boolean checkCityDTO(CityDTO city) {
        Object[] arr = new Object[]{city.getName(), city.getAltitude(), city.getLatitude(), city.getLongitude()};
        return verify(arr);
    }

    @Override
    public boolean checkCinemaDTO(CinemaDTO cinemaDTO) {
        Object[] arr = new Object[]{cinemaDTO.getName(), cinemaDTO.getAltitude(), cinemaDTO.getLatitude(), cinemaDTO.getLongitude(), cinemaDTO.getCity()};
        return verify(arr);
    }

    @Override
    public boolean checkCategoryDTO(CategoryDTO categoryDTO) {
        Object[] arr = new Object[]{categoryDTO.getName()};
        return verify(arr);
    }

    @Override
    public boolean verify(Object[] obj) {
        return Stream.of(obj).allMatch(Objects::nonNull);
    }



}