package fr.loual.mvcthymeleaf.repositories;

import fr.loual.mvcthymeleaf.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
