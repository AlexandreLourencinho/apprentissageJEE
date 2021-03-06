package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Cinema;
import fr.loual.cinemabackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {

    Room findByNameAndCinema(String name, Cinema cinema);
    List<Room> findByCinema(Cinema cinema);

}
