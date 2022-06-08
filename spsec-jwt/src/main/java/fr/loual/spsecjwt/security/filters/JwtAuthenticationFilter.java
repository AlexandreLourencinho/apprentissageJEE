package fr.loual.spsecjwt.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.loual.spsecjwt.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
            throws IOException, ServletException {
        System.out.println("successful authentication");
        User user = (User) authResult.getPrincipal(); // cast car getprincipal() retourne un type Object
        Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername()) // subject
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN)) // expiration : 5 (minutes) *60 (secondes) * 1000 (milisecondes)
                .withIssuer(request.getRequestURL().toString()) // origine du token
                //.withClaim("roles", user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList())) // collection des
                //rôles en liste de String
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())) // comme au
                // dessus mais en lambda
                .sign(algorithm);

        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername()) // subject
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_REFRESH_TOKEN)) // expiration : 5 (minutes) *60 (secondes) * 1000 (milisecondes)
                .withIssuer(request.getRequestURL().toString()) // origine du token
                .sign(algorithm);
        Map<String,String> idToken = new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefreshToken);
        response.setContentType("application/json"); // set le content type
        new ObjectMapper().writeValue(response.getOutputStream(), idToken); // écrit une valeur sur un objet (ici objet response) dans le corps de la response
    }
}  // @TODO refactor
