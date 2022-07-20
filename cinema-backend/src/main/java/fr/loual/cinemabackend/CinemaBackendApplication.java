package fr.loual.cinemabackend;

import fr.loual.cinemabackend.security.entities.AppUser;
import fr.loual.cinemabackend.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class CinemaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaBackendApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
          accountService.addNewUser(new AppUser(null, "Alex9019","Lourencinho", "Alexandre", "1234","alexandre9019@wanadoo.fr", null));
        };
    }

}
