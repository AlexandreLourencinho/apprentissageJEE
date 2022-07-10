package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
