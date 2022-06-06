package fr.loual.mvcthymeleaf.security.service;

import fr.loual.mvcthymeleaf.security.entities.AppRole;
import fr.loual.mvcthymeleaf.security.entities.AppUser;
import fr.loual.mvcthymeleaf.security.repositories.AppRoleRepository;
import fr.loual.mvcthymeleaf.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Slf4j @AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) throw new RuntimeException("Les mots de passe ne correspondent pas");
        String hashedPw = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPw);
        appUser.setActive(true);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole saveNewRole(String roleName, String roleDescription) {
        AppRole searchedRole = appRoleRepository.findByRoleName(roleName);
        if(searchedRole != null) throw new RuntimeException("le rôle" + roleName + "existe déjà");
        AppRole appRole = new AppRole();
        appRole.setRoleId(UUID.randomUUID().toString());
        appRole.setRoleName(roleName);
        appRole.setRoleDescription(roleDescription);
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appUser == null) throw new RuntimeException("l'utilisateur n'a pas été trouvé.");
        if (appRole == null) throw new RuntimeException("le rôle n'a pas été trouvé.");
        appUser.getAppRoles().add(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {

        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appUser == null) throw new RuntimeException("l'utilisateur n'a pas été trouvé.");
        if (appRole == null) throw new RuntimeException("le rôle n'a pas été trouvé.");
        appUser.getAppRoles().remove(appRole);
        appUserRepository.save(appUser);

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) throw new RuntimeException("l'utilisateur n'a pas été trouvé");
        return appUser;
    }

}
