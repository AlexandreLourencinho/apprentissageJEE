package fr.loual.spsecjwt.security.services;

import fr.loual.spsecjwt.security.entities.AppRole;
import fr.loual.spsecjwt.security.entities.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String rolename);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();

}
