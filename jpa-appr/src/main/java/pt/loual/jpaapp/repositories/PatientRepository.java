package pt.loual.jpaapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.loual.jpaapp.entities.Patient;

import java.util.Date;
import java.util.List;

/**
 * repot des patients
 */
public interface PatientRepository extends JpaRepository<Patient,Long> {

    /**
     *
     * @param m boolean
     * @return une liste
     */
    List<Patient> findByMalade(boolean m);

    /**
     *
     * @param m boolean
     * @param page type pageable
     * @return type Page
     */
    Page<Patient> findByMalade(boolean m, Pageable page);


    /**
     *
     * @param bool
     * @param score
     * @return
     */
    List<Patient> findByMaladeAndScoreLessThan(boolean bool,int score);

    /**
     *
     * @param nom
     * @param scoreMax
     * @return
     */
    @Query("select p from Patient p where p.nom like :x and p.score<:y")
    List<Patient> chercherPatient(@Param("x")String nom, @Param("y") int scoreMax);


}
