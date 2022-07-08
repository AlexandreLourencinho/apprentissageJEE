package fr.loual.cinemabackend.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.loual.cinemabackend.security.JWTUtils;
import fr.loual.cinemabackend.security.entities.AppRole;
import fr.loual.cinemabackend.security.entities.AppUser;
import fr.loual.cinemabackend.security.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service @AllArgsConstructor @NoArgsConstructor
public class JWTService {

    private AccountService accountService;

    public Map<String, String> getIdToken(@Nullable User user, @Nullable String authToken, String requestUrl, boolean needRefresh) throws UserNotFoundException {

        Algorithm algorithm = Algorithm.HMAC256(JWTUtils.SECRET_KEY);
        AppUser appUser = null;
        String jwtAccessToken;

        if (authToken != null) {
            HashMap<String, List<String>> decodedJwt = this.getDecodedJWTElements(algorithm,authToken);
            String username = decodedJwt.get("username").get(0);
            appUser = accountService.loadUserByUsername(username);
        }
        if (user != null || appUser != null) {
            jwtAccessToken = JWT.create()
                    .withSubject(user != null ? user.getUsername() : appUser.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtils.EXPIRE_ACCESS_TOKEN))
                    .withIssuer(requestUrl)
                    .withClaim("roles", user != null ? user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                            : appUser.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String, String> idToken = new HashMap<>();
            idToken.put("access-token", jwtAccessToken);
            if(needRefresh) {

                String jwtRefreshToken = JWT.create()
                        .withSubject( user != null ? user.getUsername() : appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtils.EXPIRE_REFRESH_TOKEN))
                        .withIssuer(requestUrl)
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
        String token = authToken.substring(JWTUtils.PREFIX.length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        username.add(decodedJWT.getSubject());
        if(!Objects.equals(decodedJWT.getClaim("roles").toString(), "Null Claim")) roles.addAll(decodedJWT.getClaim("roles").asList(String.class));
        decodedJwtElements.put("username",username);
        decodedJwtElements.put("roles",roles);
        return decodedJwtElements;
    }

}
