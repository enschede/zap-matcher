package nl.zzpmatcher.controllers.user;

import nl.zzpmatcher.business.User;
import nl.zzpmatcher.business.UserManagement;
import nl.zzpmatcher.business.UserRegisterCommand;
import nl.zzpmatcher.business.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    private final UserManagement userManagement;

    private final UserRepository userRepository;
    private final UserResourceAssembler userResourceAssembler;

    @Autowired
    public UserController(UserRepository userRepository, UserResourceAssembler userResourceAssembler) {
        this.userRepository = userRepository;
        this.userResourceAssembler = userResourceAssembler;

        this.userManagement = new UserManagement(userRepository);
    }

    @ExceptionHandler(UserManagement.DuplicateEmailAddressException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Double!!")
    public void handleDataFormatException(UserManagement.DuplicateEmailAddressException ex, HttpServletResponse response) {

    }

    @PostMapping("/public/createUser")
    public Resource postCreateUser(@RequestBody UserRegisterCommand userRegisterCommand) {

        User createdUser = userManagement.createUser(userRegisterCommand);

        return userResourceAssembler.toEmailAddressOnlyProjection(createdUser);
    }

    @GetMapping("/admin/user")
    public HttpEntity<Resources<User>> getUser() {

        final Iterable<User> users = userRepository.findAll();

        Resources<User> userResource = userResourceAssembler.toResources(users);
        return ResponseEntity.ok(userResource);
    }
}