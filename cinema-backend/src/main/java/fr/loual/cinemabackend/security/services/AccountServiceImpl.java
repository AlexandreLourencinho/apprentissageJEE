package fr.loual.cinemabackend.security.services;

import fr.loual.cinemabackend.security.entities.AppRole;
import fr.loual.cinemabackend.security.entities.AppUser;
import fr.loual.cinemabackend.security.exceptions.NullRoleRequiredFieldException;
import fr.loual.cinemabackend.security.exceptions.NullUserRequiredFieldException;
import fr.loual.cinemabackend.security.exceptions.RoleNotFoundException;
import fr.loual.cinemabackend.security.exceptions.UserNotFoundException;
import fr.loual.cinemabackend.security.repositories.AppRoleRepository;
import fr.loual.cinemabackend.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private AppRoleRepository roleRepository;
    private AppUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(AppUser user) throws NullUserRequiredFieldException {
        log.info("Enregistrement d'un nouvel utilisateur...");
        if (!checkRequiredUserField(user)) throw new NullUserRequiredFieldException("Les champs ne sont pas tous remplis");
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser user1 = userRepository.save(user);
        log.info("Enregistrement de l'utilisateur " + user1.getUsername() + " réussi.");
        return user1;
    }

    @Override
    public AppRole addNewRole(AppRole role) throws NullRoleRequiredFieldException {
        log.info("Ajout d'un nouveau rôle...");
        if (!checkRequiredRoleField(role)) throw new NullRoleRequiredFieldException("Le nom du rôle doit être précisé.");
        AppRole role1 = roleRepository.save(role);
        log.info("Ajout du rôle " + role1.getName() + " réussi.");
        return role1;
    }

    @Override
    public void addRoleToUser(AppUser user, AppRole role) throws RoleNotFoundException, UserNotFoundException {
        log.info("Ajout d'un role à un utilisateur...");
        AppRole findRole = roleRepository.findAppRoleByName(role.getName());
        AppUser findUser = loadUserByUsername(user.getUsername());
        if(findRole == null) throw new RoleNotFoundException("Le rôle n'existe pas.");
        if(findUser == null) throw new UserNotFoundException("L'utilisateur n'a pas été trouvé.");
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("Le rôle " + role.getName() + " à bien été assigné à l'utilisateur " + user.getUsername() + ".");
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UserNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException("L'utilisateur n'a pas été trouvé.");
        return user;
    }

    @Override
    public List<AppUser> listUsers() throws UserNotFoundException {
        List<AppUser> listUsers = userRepository.findAll();
        if(listUsers.isEmpty()) throw new UserNotFoundException("La liste des utilisateurs n'a pas pu être récupérée. Veuillez contacter un administrateur.");
        return listUsers;
    }

    @Override
    public List<AppRole> listRoles() throws RoleNotFoundException {
        List<AppRole> listRoles = roleRepository.findAll();
        if (listRoles.isEmpty()) throw new RoleNotFoundException("La liste des rôles n'a pas pu être récupérée. Veuillez Contactez un administrateur.");
        return null;
    }

    @Override
    public boolean checkRequiredUserField(AppUser user) {
        return Stream.of(user.getUsername(), user.getName(),
                user.getFirstname(), user.getEmail()).allMatch(Objects::nonNull);
    }

    @Override
    public boolean checkRequiredRoleField(AppRole role) {
        return role.getName()!=null;
    }
}
