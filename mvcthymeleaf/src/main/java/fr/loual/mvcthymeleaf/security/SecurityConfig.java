package fr.loual.mvcthymeleaf.security;

import fr.loual.mvcthymeleaf.security.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private PasswordEncoder passwordEncoder;
//    private DataSource dataSource;
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        /* inMemoryAuthentication est utile au developpement pour tester la sécurité. Les utilisateurs sont en mémoire et pas en bdd.
         String encodedPwd = passwordEncoder.encode("1234");
        System.out.println(encodedPwd);
        auth.inMemoryAuthentication() /
                .withUser("user1").password(encodedPwd).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder.encode("12345")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("010101")).roles("USER","ADMIN");
                //.withUser("admin").password("{noop}1234").roles("USER","ADMIN"); // {noop} = no password encoder */

        /*jdbc authentication
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select users_username as principal, roles_role as role from users_roles where users_username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);

         */

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); // formulaire d'authentification par défaut .loginPage("/pathtologin") pour faire sa propre page de connexion
        http.authorizeRequests().antMatchers("/").permitAll(); // le path / ne nécessite pas d'autentification
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/registration").permitAll();
        http.authorizeRequests().antMatchers("/webjars/**").permitAll(); // (accès aux ressources statiques de bootstrap ici)
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER"); // définit les url utilisable selon les roles
        http.authorizeRequests().anyRequest().authenticated(); // toutes les requetes http nécessitent une authentification
        http.exceptionHandling().accessDeniedPage("/error/403");
    }

}
