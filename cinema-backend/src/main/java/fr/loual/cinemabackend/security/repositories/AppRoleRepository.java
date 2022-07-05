package fr.loual.cinemabackend.security.repositories;

import fr.loual.cinemabackend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findAppRoleByName(String name);

}
