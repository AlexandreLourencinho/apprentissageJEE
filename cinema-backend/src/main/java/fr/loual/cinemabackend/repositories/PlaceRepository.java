package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, String> {
}
