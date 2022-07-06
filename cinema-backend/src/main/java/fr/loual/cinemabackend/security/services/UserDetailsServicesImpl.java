package fr.loual.cinemabackend.security.services;

import fr.loual.cinemabackend.security.entities.AppUser;
import fr.loual.cinemabackend.security.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service @AllArgsConstructor
public class UserDetailsServicesImpl implements UserDetailsService {

    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser appUser = accountService.loadUserByUsername(username);
            Collection<GrantedAuthority> authorities = appUser.getRoles()
                    .stream().map(appRole -> new SimpleGrantedAuthority(appRole.getName()))
                    .collect(Collectors.toList());
            return new User(appUser.getUsername(), appUser.getPassword(),authorities);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
