package fr.loual.mvcthymeleaf.security.service;

import fr.loual.mvcthymeleaf.security.entities.AppRole;
import fr.loual.mvcthymeleaf.security.entities.AppUser;

public interface SecurityService {

    AppUser saveNewUser(String username, String password, String confirmPassword);
    AppRole saveNewRole(String roleName, String roleDescription);
    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);
    AppUser loadUserByUsername(String username);

}
