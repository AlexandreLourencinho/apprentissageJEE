package fr.loual.spsecjwt.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.loual.spsecjwt.security.JWTUtil;
import fr.loual.spsecjwt.security.entities.AppRole;
import fr.loual.spsecjwt.security.entities.AppUser;
import fr.loual.spsecjwt.security.entities.RoleUserForm;
import fr.loual.spsecjwt.security.services.AccountService;
import fr.loual.spsecjwt.security.services.JWTServices;
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
       String refreshToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (refreshToken != null && refreshToken.startsWith(JWTUtil.PREFIX)) {
            try {
                JWTServices jwtServices = new JWTServices(accountService);
                Map<String,String> idToken = jwtServices.getIdToken(null,refreshToken,request.getRequestURL().toString(), false);
                idToken.put("refresh-token", refreshToken.substring(JWTUtil.PREFIX.length()));
                response.setContentType("application/json"); // set le content type
                new ObjectMapper().writeValue(response.getOutputStream(), idToken); /**/ // écrit une valeur sur un objet (ici objet response) dans le corps de la response
//                filterChain.doFilter(request, response);

            } catch(Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("refresh token required");
        }
    }

    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal) {
        return accountService.loadUserByUsername(principal.getName());
    }


    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRolename());
    }

}
