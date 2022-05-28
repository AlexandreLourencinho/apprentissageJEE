package fr.loual.hospital.repositoriestest;

import fr.loual.hospital.HospitalApplicationTests;
import fr.loual.hospital.entities.Medecin;
import fr.loual.hospital.repositories.MedecinRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedecinRepositoryTest extends HospitalApplicationTests implements TestInterface {

    @Autowired
    private MedecinRepository medecinRepository;
    private final List<String> list = new ArrayList<>(Arrays.asList("letestmedecin","DrJacques", "DrMichel"));


    @Override
    @Test
    @Order(1)
    public void testSave() {
        for( String doc:this.list) {
            Medecin medecin = new Medecin();
            medecin.setName(doc);
            medecin.setMail(doc + "@gmail.com");
            medecin.setSpeciality("MEDG");
            medecinRepository.save(medecin);
        }
    }

    @Override
    @Test
    @Order(2)
    public void testFindAll() {
        Collection<Medecin> medecinList = medecinRepository.findAll();
        Assert.notEmpty(medecinList, "médecins non trouvés");
        Assert.isTrue(medecinList.size()>2, "tous les médecins n'ont pas été enregistrés");
    }

    @Override
    @Test
    public void testFindOne() {

    }

    @Override
    @Test
    public void testUpdate() {

    }

    @Override
    @Test
    public void testDelete() {

    }
}
