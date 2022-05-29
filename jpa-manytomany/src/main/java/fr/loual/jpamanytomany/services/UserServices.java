package fr.loual.jpamanytomany.services;

import fr.loual.jpamanytomany.entities.Role;
import fr.loual.jpamanytomany.entities.User;

public interface UserServices {

    User addNewUser(User user);
    Role addNewRole(Role role);
    User findUserByUsername(String username);
    Role findRoleByRoleName(String roleName);
    void addRoleToUser(String username, String roleName);
    User authentification(String username, String password);

}
