package fr.loual.mvcthymeleaf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    @Size(min = 4, max = 40)
    private String name;
    @Temporal(TemporalType.DATE) // pour date et non timestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private boolean sickness;
    @DecimalMin("100")
    private int score;


}
