package fr.loual.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date birthdate;
    private boolean sick;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Collection<RendezVous> rendezVous;

}
