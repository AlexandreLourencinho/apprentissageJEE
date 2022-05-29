package fr.loual.jpamanytomany.services;

import fr.loual.jpamanytomany.entities.Role;
import fr.loual.jpamanytomany.entities.User;
import fr.loual.jpamanytomany.repositories.RoleRepository;
import fr.loual.jpamanytomany.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
//soit cette annotation, soit le constructeur dans la classe. Autowired fonctionne aussi mais est déprécié
public class UserServicesImpl implements UserServices {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    /*étant tous déclarés en Bean via le userservice dans la fonction start pour les deux premiers ou
    // la fonction encoder() pour le troisieme, leur attribution dans le constructeur (cf annotation)
     se fait automatiquement*/


    @Override
    public User addNewUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        if (user.getRoles() != null) {
            user.getRoles().add(role);
            role.getUsers().add(user);// non nécessaire avec JPA car il le fait automatiquement
        }
        //userRepository.save(user); // non necessaire car transactionnel
    }

    @Override // spring security
    public User authentification(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("bad credentials");
        if (passwordEncoder.matches(password, user.getPassword())) return user;
        throw new RuntimeException("bad credentials");
    }
}
