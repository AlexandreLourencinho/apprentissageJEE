package fr.loual.hospital.repositoriestest;

import fr.loual.hospital.HospitalApplicationTests;
import fr.loual.hospital.entities.Patient;
import fr.loual.hospital.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class PatientRepositoryTests extends HospitalApplicationTests {

    @Autowired
    private PatientRepository patientRepository;
    private String name = "letestjunitdebase";

    @Test
    public void testSavePatient() {
        Patient patient = new Patient();
        patient.setName(this.name);
        patient.setBirthdate(new Date());
        patient.setSick(true);
        Patient savedPatient = patientRepository.save(patient);
        System.out.println(savedPatient);
    }

    @Test
    public void testFindPatient() {
        Patient patient = patientRepository.findByName(this.name);
        Assert.notNull(patient,"patient non trouvé");
    }

    @Test
    public void testFindAllPatients() {
        Collection<Patient> patientList = patientRepository.findAll();
        Assert.notEmpty(patientList, "liste de patients non trouvée");
    }

    @Test
    public void testUpdatePatient() {
        String randomUUID = UUID.randomUUID().toString();
        Patient patient = patientRepository.findByName(this.name);
        patient.setName(randomUUID);
        patientRepository.save(patient);
        Patient patient1 = patientRepository.findByName(randomUUID);
        System.out.println(patient1);
        Assert.notNull(patient1, "le patient n'a pas été modifié");
        patient.setName(this.name);
        patientRepository.save(patient);
    }

    @Test
    public void testDeletePatient() {
        Patient patient = patientRepository.findByName("letestjunitdebase");
        Assert.notNull(patient," patient non trouvé ");
        patientRepository.delete(patient);
        patient = patientRepository.findById(4L).orElse(null);
        Assert.isNull(patient, "le patient n'a pas été supprimé");
    }


}
