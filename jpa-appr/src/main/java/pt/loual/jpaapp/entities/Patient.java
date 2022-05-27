package pt.loual.jpaapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50) // name : changer le nom de la colonne
    private String nom;
    @Temporal(TemporalType.DATE) // date : juste la date, time : jsute l'heure, timestamp: les deux
    private Date birthdate;
    private boolean malade;
    private int score;



}
