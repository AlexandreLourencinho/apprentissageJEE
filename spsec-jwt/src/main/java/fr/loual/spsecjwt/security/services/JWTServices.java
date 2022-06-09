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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service @AllArgsConstructor @NoArgsConstructor
public class JWTServices {

    private AccountService accountService;

    public Map<String, String> gett(@Nullable User user, @Nullable String authToken, String requestUrl, boolean needRefresh) {
        Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
        AppUser appUser = null;
        String jwtAccessToken;
        if (authToken != null) {
            String username = getDecodedUsername(algorithm, authToken);
            appUser = accountService.loadUserByUsername(username);
        }

        if (user != null || appUser != null) {
            jwtAccessToken = JWT.create()
                    .withSubject(user != null ? user.getUsername() : appUser.getUsername()) // subject
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN)) // expiration : 5 (minutes) *60 (secondes) * 1000 (milisecondes)
                    .withIssuer(requestUrl) // origine du token
                    //.withClaim("roles", user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList())) // collection des
                    //rôles en liste de String
                    .withClaim("roles", user != null ? user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                            : appUser.getAppRoles().stream().map(AppRole::getRolename).collect(Collectors.toList())) // comme au
                    // dessus mais en lambda
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

    public String getDecodedUsername(Algorithm algorithm, String authToken) {
        return getDecodedJwt(algorithm, authToken).getSubject();
    }

    public List<String> getDecodedRoles(Algorithm algorithm, String authToken) {
        return getDecodedJwt(algorithm, authToken).getClaim("roles").asList(String.class);
    }

    public DecodedJWT getDecodedJwt(Algorithm algorithm, String authToken) {
        String token = authToken.substring(JWTUtil.PREFIX.length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
