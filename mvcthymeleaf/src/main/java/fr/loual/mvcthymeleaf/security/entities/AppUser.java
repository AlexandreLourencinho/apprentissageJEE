package fr.loual.mvcthymeleaf.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {


    @Id
    private String userId;
    @Column(unique = true, length = 40)
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private boolean active;
//    @Transient
//    private String confirmPassword;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> appRoles = new ArrayList<>();


}
