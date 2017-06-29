package nl.zzpmatcher.userLogon.controller;

import nl.zzpmatcher.userLogon.business.UserLoginCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public SecurityController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/public/login")
    public HttpEntity login(@RequestBody UserLoginCommand userLoginCommand) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userLoginCommand.getUsername(), userLoginCommand.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            Resource<UserEmailaddressOnlyProjection> resource =
                    new Resource<>(new UserEmailaddressOnlyProjection(SecurityContextHolder.getContext().getAuthentication().getName()));
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
