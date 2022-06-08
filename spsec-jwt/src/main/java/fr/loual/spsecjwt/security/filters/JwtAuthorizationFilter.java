package fr.loual.spsecjwt.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.loual.spsecjwt.security.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/refreshToken") || request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            String jwtAuthorizationToken = request.getHeader(JWTUtil.AUTH_HEADER);

            if(jwtAuthorizationToken != null && jwtAuthorizationToken.startsWith(JWTUtil.PREFIX)) {

                try {
                    String jwt = jwtAuthorizationToken.substring(JWTUtil.PREFIX.length()); // récupération du token
                    Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET); // même clef que pour la signature
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build(); // création du verifier du token
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
//                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    for (String role:roles) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                            null,
                            authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
                    response.setHeader("error-message",e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
//                response.sendError(403); // (pareil qu'au dessus)
                }  // @TODO refactor

            } else {
                filterChain.doFilter(request, response);
            }
        }


    }
}
