package fr.loual.cinemabackend.services;

import fr.loual.cinemabackend.dtos.*;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
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
    public boolean checkMovieDTO(MovieDTO movieDTO) {
        boolean category = checkCategoryDTO(movieDTO.getCategory());
        Object[] arr = new Object[]{movieDTO.getMaker(), movieDTO.getTitle(), movieDTO.getPicture(),movieDTO.getReleaseate(), movieDTO.getDuration()};
        boolean movie = verify(arr);
        return category && movie;
    }

    @Override
    public boolean checkRoomDTO(RoomDTO roomDTO) {
        Object[] arr = new Object[]{roomDTO.getName(), roomDTO.getRoomSize(), roomDTO.getCinema()};
        return verify(arr);
    }


    @Override
    public boolean verify(Object[] obj) {
        return Stream.of(obj).allMatch(Objects::nonNull);
    }



}
