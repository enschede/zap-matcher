package nl.zzpmatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
//    @Qualifier(value = "authenticationManager")
    private AuthenticationManager authenticationManager;

    @PostMapping("/public/login")
    public HttpEntity login(@RequestBody UserLoginCommand userLoginCommand) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userLoginCommand.getUsername(), userLoginCommand.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            Resource<String> resource = new Resource<>("");
            UserResourceAssembler.addLogoutLinks(resource);
            return ResponseEntity.ok(resource);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/logout")
    public HttpEntity logout() {
        SecurityContextHolder.clearContext();

        Resource<String> resource = new Resource("");
        UserResourceAssembler.addLoginLink(resource);
        return ResponseEntity.ok(resource);
    }

}
