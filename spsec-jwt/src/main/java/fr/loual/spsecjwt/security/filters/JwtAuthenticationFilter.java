package fr.loual.spsecjwt.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.loual.spsecjwt.security.services.JWTServices;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attempt authentication");
        String username = request.getParameter("username"); // récupération username (formulaire)
        String password = request.getParameter("password"); // récupération mdp (formulaire)
        System.out.println("username = " + username + " || password = " + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException {
        System.out.println("successful authentication");
        User user = (User) authResult.getPrincipal(); // cast car getprincipal() retourne un type Object
        JWTServices jwtServices = new JWTServices();
        Map<String,String> idToken =jwtServices.gett(user, null, request.getRequestURL().toString());
        response.setContentType("application/json"); // set le content type
        new ObjectMapper().writeValue(response.getOutputStream(), idToken); // écrit une valeur sur un objet (ici objet response) dans le corps de la response
    }
}
