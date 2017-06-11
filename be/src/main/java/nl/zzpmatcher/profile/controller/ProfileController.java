package nl.zzpmatcher.profile.controller;

import nl.zzpmatcher.profile.business.ProfileManagement;
import nl.zzpmatcher.profile.business.UpdateProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ProfileController {

    private ProfileRepository profileRepository;

    @Autowired
    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping("/user/profile")
    public HttpEntity postProfile(@RequestBody UpdateProfileCommand updateProfileCommand) {

        new ProfileManagement(profileRepository).updateProfile(updateProfileCommand);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ProfileManagement.InvalidUsernameException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid username")
    public void handleDataFormatException(ProfileManagement.InvalidUsernameException ex, HttpServletResponse response) {

    }

}
