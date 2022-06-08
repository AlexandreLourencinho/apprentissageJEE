package fr.loual.spsecjwt.security.repositories;

import fr.loual.spsecjwt.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

    AppRole findByRolename(String rolename);
}
