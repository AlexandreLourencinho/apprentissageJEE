package fr.loual.cinemabackend.security;

import fr.loual.cinemabackend.security.filters.JWTAuthenticationFilter;
import fr.loual.cinemabackend.security.filters.JWTAuthorizationFilter;
import fr.loual.cinemabackend.security.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JWTService jwtService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/h2-console/**", "/refreshToken/**", "/login/**", "/register","/swagger-ui/index.html/**").permitAll();
//        http.authorizeRequests().anyRequest().permitAll(); // TODO supprimer après les tests
        http.headers().frameOptions().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new JWTAuthorizationFilter(this.jwtService), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new JWTAuthenticationFilter(this.authenticationManagerBean()));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
