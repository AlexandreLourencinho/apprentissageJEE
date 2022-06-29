package fr.loual.hospital;

import fr.loual.hospital.entities.*;
import fr.loual.hospital.services.IHospitalServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Bean // méthode avec @Bean va s'éxécuter au démarrage et l'objet retourné devient un composant Spring +-@component
    public CommandLineRunner start(IHospitalServices hospitalServices) {
        return args -> {
            Stream.of("Alexandre","Morgane","Jean-Charles")
                    .forEach( name -> {
                Patient patient = new Patient();
                patient.setBirthdate(new Date());
                patient.setName(name);
                patient.setSick(false);
                hospitalServices.savePatient(patient);
            });

            Stream.of("DrUn","DrDeux")
                    .forEach( name -> {
                        Medecin medecin = new Medecin();
                        medecin.setSpeciality(Math.random()>0.5? "Cardio" : "dentiste");
                        medecin.setMail(name + "@medecin.com");
                        medecin.setName(name);
                        hospitalServices.saveMedecin(medecin);
                    });

            Patient patient = hospitalServices.findPatientById(1L);
            Patient patient2 = hospitalServices.findPatientByName("Morgane");

            Medecin medecin = hospitalServices.findMedecinByName("DrUn");

            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(new Date());
            rendezVous.setStatus(StatusRdv.PENDING);
            rendezVous.setPatient(patient2);
            rendezVous.setMedecin(medecin);

            RendezVous rdv = hospitalServices.saveRendezVous(rendezVous);
            System.out.println(rdv.getId() + "/date : /" + rdv.getDate() + "/medecin : /" + rdv.getMedecin() + "/patient : /" + rdv.getPatient());

            Consultation consultation = new Consultation();
            RendezVous rendezVous1 = hospitalServices.findRendezVousById(1L);
            consultation.setDateConsultation(rendezVous1.getDate());
            consultation.setRendezVous(rendezVous1);
            consultation.setRapport("Rapport de la consultation : le patient est vraiment en très bonne santé");
            hospitalServices.saveConsultation(consultation);

        };
    }

}



/*
mieux que d'implementer l'interface commandlinerunner car permet l'injection directement dans
la méthode start (cdf ci dessus)

 */