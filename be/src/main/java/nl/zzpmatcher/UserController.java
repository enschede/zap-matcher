package nl.zzpmatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserResourceAssembler userResourceAssembler;

    @Autowired
    public UserController(UserRepository userRepository, UserResourceAssembler userResourceAssembler) {
        this.userRepository = userRepository;
        this.userResourceAssembler = userResourceAssembler;
    }

    @PostMapping("/public/createUser")
    public Resource postCreateUser(@RequestBody UserCreateCommand userCreateCommand) {

        Validator.validate(isEmailAddressUnique(userCreateCommand.getEmailaddress()), () -> new RuntimeException("EmailAddress already exists"));

        final User createdUser = userRepository.save(userCreateCommand.createUser());

        return userResourceAssembler.toEmailAddressOnlyProjection(createdUser);
    }

    private boolean isEmailAddressUnique(String emailAddress) {
        return userRepository.findUserByEmailaddress(emailAddress) == null;
    }

    @GetMapping("/admin/user")
    public HttpEntity<Resources<User>> getUser() {

        final Iterable<User> users = userRepository.findAll();

        Resources<User> userResource = userResourceAssembler.toResources(users);
        return ResponseEntity.ok(userResource);
    }

}
