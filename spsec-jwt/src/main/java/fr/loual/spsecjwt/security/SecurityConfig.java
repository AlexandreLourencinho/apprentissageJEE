package fr.loual.spsecjwt.security;

import fr.loual.spsecjwt.security.filters.JwtAuthenticationFilter;
import fr.loual.spsecjwt.security.filters.JwtAuthorizationFilter;
import fr.loual.spsecjwt.security.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // @TODO voir pour remplacer le WebSecurityConfigurerAdapter (deprecated) - note : potentiellement pas encore de doc

    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // désactive le CSRF protection, ici pour utilisation h2
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // authentification de type stateless
        http.authorizeRequests().antMatchers("/h2-console/**", "/refreshToken/**", "/login/**").permitAll();
        http.headers().frameOptions().disable(); // désactive les protections sur les frames - aussi ici pour h2
//        http.formLogin();
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/**").hasAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/**").hasAuthority("USER"); utilisation de àa ou de EnableGlobalMethodSecurity
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);// premier filtre recevant la requête
        http.addFilter(new JwtAuthenticationFilter(this.authenticationManagerBean()));

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
