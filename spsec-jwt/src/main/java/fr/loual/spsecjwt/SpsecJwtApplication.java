package fr.loual.spsecjwt;

import fr.loual.spsecjwt.security.entities.AppRole;
import fr.loual.spsecjwt.security.entities.AppUser;
import fr.loual.spsecjwt.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // définit les anotations utilisable
public class SpsecJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpsecJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args-> {
            accountService.addNewRole(new AppRole(null, "USER"));
            accountService.addNewRole(new AppRole(null, "ADMIN"));
            accountService.addNewRole(new AppRole(null, "CUSTOMER_MANAGER"));
            accountService.addNewRole(new AppRole(null, "PRODUCT_MANAGER"));
            accountService.addNewRole(new AppRole(null, "BILLS_MANAGER"));

            Stream.of("Alexandre", "Morgane", "Pauline", "Joseph").forEach(name -> {
                accountService.addNewUser(new AppUser(null, name, "1234", new ArrayList<>()));
            });

            accountService.addRoleToUser("Alexandre", "USER");
            accountService.addRoleToUser("Alexandre", "ADMIN");
            accountService.addRoleToUser("Morgane", "USER");
            accountService.addRoleToUser("Morgane", "CUSTOMER_MANAGER");
            accountService.addRoleToUser("Pauline", "USER");
            accountService.addRoleToUser("Pauline", "PRODUCT_MANAGER");
            accountService.addRoleToUser("Joseph", "USER");
            accountService.addRoleToUser("Joseph", "BILLS_MANAGER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
