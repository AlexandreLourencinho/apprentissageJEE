package fr.loual.spsecjwt.security.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.loual.spsecjwt.security.JWTUtil;
import fr.loual.spsecjwt.security.entities.AppRole;
import fr.loual.spsecjwt.security.entities.AppUser;
import fr.loual.spsecjwt.security.entities.RoleUserForm;
import fr.loual.spsecjwt.security.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController @AllArgsConstructor
public class AccountRestController {

    private AccountService accountService;

    @GetMapping(path = "/users")
    @PostAuthorize("hasAuthority('USER')") // méthode accessible uniquement pour ceux ayant le role USER *
    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }

    @PostMapping(path = "/users")
    @PostAuthorize("hasAuthority('ADMIN')") // méthode accessible uniquement pour ceux ayant le role ADMIN *
    public AppUser saveUser(@RequestBody AppUser appUser) {
        return accountService.addNewUser(appUser);
    }

    @PostMapping(path = "/roles")
    @PostAuthorize("hasAuthority('ADMIN')") // méthode accessible uniquement pour ceux ayant le role ADMIN *
    public AppRole saveUser(@RequestBody AppRole appRole) {
        return accountService.addNewRole(appRole);
    }
// * : activé grace à l'annotation @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
       /**/ String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        /**/if (authToken != null && authToken.startsWith(JWTUtil.PREFIX)) {
            /**/try {
                /**/String refreshToken = authToken.substring(JWTUtil.PREFIX.length());
                /**/Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                /**/JWTVerifier verifier = JWT.require(algorithm).build();
                /**/DecodedJWT decodedJWT = verifier.verify(refreshToken);
                /**/String username = decodedJWT.getSubject();
                AppUser appUser = accountService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername()) // subject
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN)) // expiration : 5 (minutes) *60 (secondes) * 1000 (milisecondes)
                        .withIssuer(request.getRequestURL().toString()) // origine du token
                        //.withClaim("roles", user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList())) // collection des
                        //rôles en liste de String
                        .withClaim("roles", appUser.getAppRoles().stream().map(AppRole::getRolename).collect(Collectors.toList())) // comme au
                        // dessus mais en lambda
                        .sign(algorithm);
                Map<String,String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", refreshToken);
                response.setContentType("application/json"); // set le content type
                new ObjectMapper().writeValue(response.getOutputStream(), idToken); // écrit une valeur sur un objet (ici objet response) dans le corps de la response
//                filterChain.doFilter(request, response);

            } catch(Exception e) {

                throw e;
            }
        } else {
            throw new RuntimeException("refresh token required");
        }
    } // @TODO refactor

    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal) {
        return accountService.loadUserByUsername(principal.getName());
    }


    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRolename());
    }

}
