package fr.loual.mvcthymeleaf.security.service;

import fr.loual.mvcthymeleaf.security.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service @AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUsername(username);
        /*Collection<GrantedAuthority> authorities = new ArrayList<>(); // type nécessaire au User de spgSec pour les roles
        appUser.getAppRoles().forEach(appRole -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appRole.getRoleName());
            authorities.add(authority);
        }); // opération de transfert de roles vers une collection de GrantedAuthority
          méthode 1 */

        /* méthode 2 en utilisant le .stream */
        Collection<GrantedAuthority> authorities =
                appUser.getAppRoles()
                        .stream()
                        .map(appRole -> new SimpleGrantedAuthority(appRole.getRoleName()))
                        .collect(Collectors.toList());

        return new User(appUser.getUsername(), appUser.getPassword(), authorities); // classe User de spring security
    }


}
