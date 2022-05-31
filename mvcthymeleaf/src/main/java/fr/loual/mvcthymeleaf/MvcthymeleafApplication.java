package fr.loual.mvcthymeleaf;

import fr.loual.mvcthymeleaf.entities.Patient;
import fr.loual.mvcthymeleaf.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
                patient.setScore((int) (Math.random() * 1000));
                patient.setSickness(Math.random() > 0.5);
                patientRepository.save(patient);
            });

            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getName() + "//");
            });
        };
    }

}
