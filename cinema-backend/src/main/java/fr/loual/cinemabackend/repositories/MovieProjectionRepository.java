package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieProjectionRepository extends JpaRepository<Seance, String> {
}
