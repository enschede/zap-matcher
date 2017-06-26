package nl.zzpmatcher.profile.controller;

import nl.zzpmatcher.profile.business.Profile;
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

    @CrossOrigin(origins = "http://local.zzpmatcher.nl:4200")
    @PostMapping("/user/profile")
    public HttpEntity<ProfileProjection> postProfile(@RequestBody UpdateProfileCommand updateProfileCommand) {

        final Profile profile = new ProfileManagement(profileRepository).updateProfile(updateProfileCommand);

        return ResponseEntity.ok(ProfileProjection.of(profile));
    }

    @CrossOrigin(origins = "http://local.zzpmatcher.nl:4200")
    @GetMapping("/user/profile")
    public HttpEntity<ProfileProjection> getProfile() {

        final Profile profile = new ProfileManagement(profileRepository).getProfile();

        return ResponseEntity.ok(ProfileProjection.of(profile));
    }

    @ExceptionHandler(ProfileManagement.InvalidUsernameException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid username")
    public void handleDataFormatException(ProfileManagement.InvalidUsernameException ex, HttpServletResponse response) {

    }

}
