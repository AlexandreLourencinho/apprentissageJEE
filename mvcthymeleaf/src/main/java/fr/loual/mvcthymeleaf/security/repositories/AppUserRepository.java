package fr.loual.mvcthymeleaf.security.repositories;

import fr.loual.mvcthymeleaf.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);
}
