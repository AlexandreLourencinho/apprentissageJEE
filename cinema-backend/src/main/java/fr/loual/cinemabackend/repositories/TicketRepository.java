package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
