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

    private final ProfileManagement profileManagement;

    @Autowired
    public ProfileController(ProfileRepository profileRepository) {
        profileManagement = new ProfileManagement(profileRepository);
    }

    @PostMapping("/user/profile")
    public HttpEntity<ProfileProjection> postProfile(@RequestBody UpdateProfileCommand updateProfileCommand) {

        final Profile profile = profileManagement.updateProfile(updateProfileCommand);

        return ResponseEntity.ok(ProfileProjection.of(profile));
    }

    @GetMapping("/user/profile")
    public HttpEntity<ProfileProjection> getProfile() {

        final Profile profile = profileManagement.getProfile();

        return ResponseEntity.ok(ProfileProjection.of(profile));
    }

    @ExceptionHandler(ProfileManagement.InvalidUsernameException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid username")
    public void handleDataFormatException(ProfileManagement.InvalidUsernameException ex, HttpServletResponse response) {

    }

}
