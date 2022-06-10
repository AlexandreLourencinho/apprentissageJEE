package fr.loual.spsecjwt.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.loual.spsecjwt.security.JWTUtil;
import fr.loual.spsecjwt.security.entities.AppRole;
import fr.loual.spsecjwt.security.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service @AllArgsConstructor @NoArgsConstructor
public class JWTServices {

    private AccountService accountService;

    public Map<String, String> getIdToken(@Nullable User user, @Nullable String authToken, String requestUrl, boolean needRefresh) {

        Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
        AppUser appUser = null;
        String jwtAccessToken;

        if (authToken != null) {
            HashMap<String, List<String>> decodedJwt = this.getDecodedJWTElements(algorithm,authToken);
            String username = decodedJwt.get("username").get(0);
            appUser = accountService.loadUserByUsername(username);
        }

        if (user != null || appUser != null) {
            jwtAccessToken = JWT.create()
                    .withSubject(user != null ? user.getUsername() : appUser.getUsername()) // subject
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN)) // expiration : 5 (minutes) *60 (secondes) * 1000 (milisecondes)
                    .withIssuer(requestUrl) // origine du token
                    //.withClaim("roles", user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList())) // collection des rôles en liste de String
                    .withClaim("roles", user != null ? user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                            : appUser.getAppRoles().stream().map(AppRole::getRolename).collect(Collectors.toList())) //comme au  dessus mais en lambda
                    .sign(algorithm);
            Map<String, String> idToken = new HashMap<>();
            idToken.put("access-token", jwtAccessToken);

            if(needRefresh) {

                String jwtRefreshToken = JWT.create()
                        .withSubject( user != null ? user.getUsername() : appUser.getUsername()) // subject
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_REFRESH_TOKEN)) // expiration : 5 (minutes) *60 (secondes) * 1000 (milisecondes)
                        .withIssuer(requestUrl) // origine du token
                        .sign(algorithm);
                idToken.put("refresh-token", jwtRefreshToken);
                
            }

            return idToken;

        } else {

            throw new RuntimeException("User et AppUser non définis");

        }
    }

    public HashMap<String, List<String>> getDecodedJWTElements(Algorithm algorithm, String authToken) {
        HashMap<String, List<String>> decodedJwtElements = new HashMap<>();
        List<String> username = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        String token = authToken.substring(JWTUtil.PREFIX.length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        username.add(decodedJWT.getSubject());
        if(!Objects.equals(decodedJWT.getClaim("roles").toString(), "Null Claim")) roles.addAll(decodedJWT.getClaim("roles").asList(String.class));
        decodedJwtElements.put("username",username);
        decodedJwtElements.put("roles",roles);
        return decodedJwtElements;
    }
}
