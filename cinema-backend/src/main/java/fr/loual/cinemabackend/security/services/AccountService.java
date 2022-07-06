package fr.loual.cinemabackend.security.services;

import fr.loual.cinemabackend.security.entities.AppRole;
import fr.loual.cinemabackend.security.entities.AppUser;
import fr.loual.cinemabackend.security.exceptions.NullRoleRequiredFieldException;
import fr.loual.cinemabackend.security.exceptions.NullUserRequiredFieldException;
import fr.loual.cinemabackend.security.exceptions.RoleNotFoundException;
import fr.loual.cinemabackend.security.exceptions.UserNotFoundException;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser user) throws NullUserRequiredFieldException;
    AppRole addNewRole(AppRole role) throws NullRoleRequiredFieldException;
    void addRoleToUser(AppUser user, AppRole role) throws RoleNotFoundException, UserNotFoundException;
    AppUser loadUserByUsername(String username) throws UserNotFoundException;
    List<AppUser> listUsers() throws UserNotFoundException;
    List<AppRole> listRoles() throws RoleNotFoundException;
    boolean checkRequiredUserField(AppUser user);
    boolean checkRequiredRoleField(AppRole role);

}
