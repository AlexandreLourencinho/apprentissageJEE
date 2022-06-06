package fr.loual.mvcthymeleaf.security.repositories;

import fr.loual.mvcthymeleaf.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, String> {

    AppRole findByRoleName(String roleName);

}
