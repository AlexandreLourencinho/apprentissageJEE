package fr.loual.mvcthymeleaf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        String encodedPwd = passwordEncoder.encode("1234");
        System.out.println(encodedPwd);
        auth.inMemoryAuthentication() //est utile au developpement pour tester la sécurité. Les utilisateurs sont en mémoire et pas en bdd.
                .withUser("user1").password(encodedPwd).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder.encode("12345")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("010101")).roles("USER","ADMIN");
                //.withUser("admin").password("{noop}1234").roles("USER","ADMIN"); // {noop} = no password encoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); // formulaire d'authentification par défaut .loginPage("/pathtologin") pour faire sa propre page de connexion
        http.authorizeRequests().antMatchers("/").permitAll(); // le path / ne nécessite pas d'autentification
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasRole("USER"); // définit les url utilisable selon les roles
        http.authorizeRequests().anyRequest().authenticated(); // toutes les requetes http nécessitent une authentification
        http.exceptionHandling().accessDeniedPage("/error/403");
    }

    @Bean // (créé au démarrage)
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
