package fr.loual.jpamanytomany.web;

import fr.loual.jpamanytomany.entities.User;
import fr.loual.jpamanytomany.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UsersController {
    private UserServices userServices;

    @GetMapping("/users/{username}")
    public User user(@PathVariable String username){
        return userServices.findUserByUsername(username);
    }

}
