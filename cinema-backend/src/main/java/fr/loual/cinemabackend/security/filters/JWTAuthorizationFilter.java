package fr.loual.cinemabackend.security.filters;

import com.auth0.jwt.algorithms.Algorithm;
import fr.loual.cinemabackend.security.JWTUtils;
import fr.loual.cinemabackend.security.services.JWTService;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/refreshToken") || request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            String jwtAuthoToken = request.getHeader(JWTUtils.HEADER);
            Algorithm algorithm = Algorithm.HMAC256(JWTUtils.SECRET_KEY);

            if(jwtAuthoToken != null && jwtAuthoToken.startsWith(JWTUtils.PREFIX)) {
                try {
                    HashMap<String, List<String>> decodedJwt = this.jwtService.getDecodedJWTElements(algorithm,jwtAuthoToken);
                    String username = decodedJwt.get("username").get(0);
                    Collection<GrantedAuthority> authorities = decodedJwt.get("roles")
                            .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {

                    response.setHeader("error-message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                filterChain.doFilter(request,response);
            }
        }
    }
}
