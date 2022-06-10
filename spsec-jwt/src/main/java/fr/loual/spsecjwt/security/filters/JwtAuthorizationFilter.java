package fr.loual.spsecjwt.security.filters;

import com.auth0.jwt.algorithms.Algorithm;
import fr.loual.spsecjwt.security.JWTUtil;
import fr.loual.spsecjwt.security.services.JWTServices;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/refreshToken") || request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            String jwtAuthorizationToken = request.getHeader(JWTUtil.AUTH_HEADER);
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET); // même clef que pour la signature

            if(jwtAuthorizationToken != null && jwtAuthorizationToken.startsWith(JWTUtil.PREFIX)) {

                try {
                    JWTServices jwtServices = new JWTServices();
                    HashMap<String, List<String>> decodedJwt = jwtServices.getDecodedJWTElements(algorithm, jwtAuthorizationToken);
                    String username = decodedJwt.get("username").get(0);
                    Collection<GrantedAuthority> authorities = decodedJwt.get("roles").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()); ;
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                            null,
                            authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
                    response.setHeader("error-message",e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
//                response.sendError(403); // (pareil qu'au dessus)
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
