package fr.loual.mvcthymeleaf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="roles")
public class Role {

    @Column(unique = true, length = 10)
    @Id
    private String role;
    @ManyToMany
    @JoinTable(name="users_roles")
    @JoinColumn(name = "role")
    private List<User> users;



}
