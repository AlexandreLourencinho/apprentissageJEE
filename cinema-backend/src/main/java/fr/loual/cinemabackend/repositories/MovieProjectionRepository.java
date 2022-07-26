package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Movie;
import fr.loual.cinemabackend.entities.Room;
import fr.loual.cinemabackend.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface MovieProjectionRepository extends JpaRepository<Seance, String> {

    List<Seance> findByMovieAndRoomCinema_IdAndBeginDateIsGreaterThan(Movie movie, String cinemaId, Date beginDate);
}
