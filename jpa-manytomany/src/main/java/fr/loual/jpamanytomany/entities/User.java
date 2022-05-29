package fr.loual.jpamanytomany.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    private String id;
    @Column(unique = true, length = 40, name = "user_name")
    private String username;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) => n'envoi pas le mdp
    private String password;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

}
