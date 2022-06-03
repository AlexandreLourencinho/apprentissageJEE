package fr.loual.mvcthymeleaf.repositories;

import fr.loual.mvcthymeleaf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
