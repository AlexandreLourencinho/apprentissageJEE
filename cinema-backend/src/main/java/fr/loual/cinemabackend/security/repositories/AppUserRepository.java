package fr.loual.cinemabackend.security.repositories;

import fr.loual.cinemabackend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {

    AppUser findByUsername(String username);

}
