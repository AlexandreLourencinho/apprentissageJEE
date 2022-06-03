package fr.loual.mvcthymeleaf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Column(unique = true, length = 40)
    @NotEmpty
    @Id
    private String username;
    private boolean active;
    @NotEmpty
    private String password;
    @Transient
    private String confirmPassword;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles;


}
