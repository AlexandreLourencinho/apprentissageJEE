package fr.loual.cinemabackend.security.web;

import fr.loual.cinemabackend.security.entities.AppRole;
import fr.loual.cinemabackend.security.entities.AppUser;
import fr.loual.cinemabackend.security.exceptions.NullRoleRequiredFieldException;
import fr.loual.cinemabackend.security.exceptions.NullUserRequiredFieldException;
import fr.loual.cinemabackend.security.exceptions.RoleNotFoundException;
import fr.loual.cinemabackend.security.exceptions.UserNotFoundException;
import fr.loual.cinemabackend.security.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @AllArgsConstructor
public class AccountRestController {

    private AccountService accountService;

    //TODO : DTOS + gestion d'erreurs
    // TODO login ok, jwt ok
    // ** test = ok

    @PostMapping("/register")
    public AppUser registerUser(@RequestBody AppUser appUser) throws NullUserRequiredFieldException {
        return accountService.addNewUser(appUser);
    }// **

    @PostMapping("/roles")
    public AppRole addNewRole(@RequestBody AppRole appRole) throws NullRoleRequiredFieldException {
        return accountService.addNewRole(appRole);
    }// **

    @PostMapping("/add-role-to-user")
    public void addRoleToUser(@RequestBody AppUser appUser, AppRole appRole) throws UserNotFoundException, RoleNotFoundException {
        accountService.addRoleToUser(appUser, appRole);
    } // **

    @GetMapping("/users")
    public List<AppUser> listUsers() throws UserNotFoundException {
        return accountService.listUsers();
    } // **





}