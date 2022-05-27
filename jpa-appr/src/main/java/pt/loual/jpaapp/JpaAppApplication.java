package pt.loual.jpaapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pt.loual.jpaapp.entities.Patient;
import pt.loual.jpaapp.repositories.PatientRepository;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaAppApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        for(int i=0; i<1000; i++) {
            patientRepository.save(new Patient(null,"truc",new Date(), Math.random() > 0.5, (int) (Math.random() * 1000)));
        }

        System.out.println(1L);
        System.out.println((long) 1);


        List<Patient> patients = patientRepository.findAll();
        Page<Patient> patients2 = patientRepository.findAll(PageRequest.of(1,10));
        System.out.println("total page :" + patients2.getTotalPages());
        System.out.println("total elements :" + patients2.getTotalElements());
        System.out.println("numero page :" + patients2.getNumber());
        System.out.println("content :" + patients2.getContent());

        patients.forEach(p->{
            System.out.println("==================================");
            System.out.println(p.getId());
            System.out.println(p.getBirthdate());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.isMalade());
        });
        patients2.forEach(p->{
            System.out.println("==================================");
            System.out.println(p.getId());
            System.out.println(p.getBirthdate());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.isMalade());
        });

        System.out.println("****************************");
        Patient patient = patientRepository.findById(1L).orElse(null);
        if (patient != null) {
            System.out.println(patient);
            patient.setScore(9000);
            patientRepository.save(patient);
        } else {
            System.out.println("pas trouvé");
        }

        List<Patient> pm = patientRepository.findByMalade(true);
        pm.forEach(p-> {
            System.out.println("nom :" + p.getNom());
            System.out.println("berk caca malade");
            System.out.println("*******************");
        });

        Page<Patient> pagepm = patientRepository.findByMalade(false,PageRequest.of(1,4));
        pagepm.forEach(pmpp-> {
            System.out.println("nom :" + pmpp.getNom());
            System.out.println("id : " + pmpp.getId());
            System.out.println("pas malade!");
            System.out.println("************");
        });

        List<Patient> pmscore = patientRepository.findByMaladeAndScoreLessThan(true,500);

        pmscore.forEach(pmp-> {
            System.out.println("nom : " + pmp.getNom());
            System.out.println("id" + pmp.getId());
            System.out.println("malade!");
            System.out.println("score : " + pmp.getScore());
            System.out.println("------------");
        });

        List<Patient> patentlist = patientRepository.chercherPatient("%u%",40);
        patentlist.forEach(patent-> {
            System.out.println("//////////");
            System.out.println("nom :" + patent.getNom());
            System.out.println("score :" + patent.getScore());
            System.out.println("id" + patent.getId());
            System.out.println("////////////////////");
        });

        patientRepository.deleteById(6L);



    }
}
