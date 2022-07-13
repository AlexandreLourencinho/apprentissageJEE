package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);
}
