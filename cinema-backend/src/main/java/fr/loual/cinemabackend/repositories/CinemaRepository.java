package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, String> {

    Cinema findByAltitudeAndLatitudeAndLongitude(double altitude, double latitude, double longitude);

}
