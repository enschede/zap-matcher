package nl.zzpmatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public User postUser(@RequestBody UserCreateCommand userCreateCommand) {

        return userRepository.save(userCreateCommand.createUser());
    }

    @GetMapping("/user")
    public Iterable<User> getUser() {

        return userRepository.findAll();
    }
}
