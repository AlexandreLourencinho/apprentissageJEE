package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Cinema;
import fr.loual.cinemabackend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, String> {

    Cinema findByAltitudeAndLatitudeAndLongitude(double altitude, double latitude, double longitude);
    List<Cinema> findByCity(City city);

}
