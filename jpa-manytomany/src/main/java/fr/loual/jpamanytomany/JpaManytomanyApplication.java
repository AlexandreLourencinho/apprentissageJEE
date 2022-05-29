package fr.loual.jpamanytomany;

import fr.loual.jpamanytomany.entities.Role;
import fr.loual.jpamanytomany.entities.User;
import fr.loual.jpamanytomany.services.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import java.util.stream.Stream;

@SpringBootApplication
public class JpaManytomanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaManytomanyApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserServices userServices) {
        return args -> {

            Stream.of("user1", "user2", "user3").forEach(user->{
                User u = new User();
                u.setUsername(user);
                u.setPassword("1234"); // voir plus tard pour le hashage avec bcrypt, pas de stockage en clair
                // par défaut évidemment...
                userServices.addNewUser(u);
            });

            Stream.of("user","student","admin").forEach(role -> {
                Role role1 = new Role();
                role1.setRoleName(role);
                userServices.addNewRole(role1);
            });

            userServices.addRoleToUser("user1", "student");
            userServices.addRoleToUser("user1", "user");
            userServices.addRoleToUser("user2", "admin");
            userServices.addRoleToUser("user3", "user");


            try {
                User user = userServices.authentification("user1", "1234");
                System.out.println("id : " + user.getId() + " / name : " + user.getUsername());
                System.out.println("roles =>");
                user.getRoles().forEach(role -> {
                    System.out.println("Role" + role);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        };
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
