package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByTitleAndMakerAndReleaseDate(String title, String maker, Date releaseDate);

}
