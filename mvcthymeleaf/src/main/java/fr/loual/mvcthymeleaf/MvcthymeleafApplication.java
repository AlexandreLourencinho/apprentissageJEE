package fr.loual.mvcthymeleaf;

import fr.loual.mvcthymeleaf.entities.Patient;
import fr.loual.mvcthymeleaf.repositories.PatientRepository;
import fr.loual.mvcthymeleaf.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class MvcthymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcthymeleafApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args-> {
            Stream.of("Alex","Jean","Michel").forEach(name -> {
                Patient patient = new Patient();
                patient.setName(name);
                patient.setBirthdate(new Date());
                patient.setScore((int) (Math.random() * 100000));
                patient.setSickness(Math.random() > 0.5);
                patientRepository.save(patient);
            });

            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getName() + "//");
            });
        };
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean
    public CommandLineRunner saveUsers(SecurityService securityService) {
        return args -> {
            securityService.saveNewUser("Alexandre","1234","1234");
            securityService.saveNewUser("Joseph", "12345", "12345");
            securityService.saveNewUser("Paul", "12345", "12345");

            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");

            securityService.addRoleToUser("Alexandre", "ADMIN");
            securityService.addRoleToUser("Alexandre", "USER");
            securityService.addRoleToUser("Joseph", "USER");
            securityService.addRoleToUser("Paul", "USER");
        };
    }

}
