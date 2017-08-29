package nl.zzpmatcher.profile.controller;

import nl.zzpmatcher.profile.business.Profile;
import nl.zzpmatcher.profile.business.ProfileManagement;
import nl.zzpmatcher.profile.business.Tag;
import nl.zzpmatcher.profile.business.UpdateProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
public class ProfileController {

    private final ProfileManagement profileManagement;

    @Autowired
    public ProfileController(ProfileRepository profileRepository) {
        profileManagement = new ProfileManagement(profileRepository);
    }

    @CrossOrigin(origins = "${zzpmatcher.cors.allowed.origin}")
    @PostMapping("/user/profile")
    public HttpEntity<ProfileProjection> postProfile(@RequestBody UpdateProfileCommand updateProfileCommand) {

        final Profile profile = profileManagement.updateProfile(updateProfileCommand);

        return ResponseEntity.ok(ProfileProjection.of(profile));
    }

    @CrossOrigin(origins = "${zzpmatcher.cors.allowed.origin}")
    @GetMapping("/user/profile")
    public HttpEntity<ProfileProjection> getProfile() {

        Profile profile = profileManagement.getProfile();
//        profile = getProfileDummy();


        final ResponseEntity<ProfileProjection> ok = ResponseEntity.ok(ProfileProjection.of(profile));

        return ok;
    }

    private Profile getProfileDummy() {
        final Profile profile = new Profile();

        profile.setId("jansen@jansen.com");
        profile.setTags(Arrays.asList(Tag.of(profile, "java"), Tag.of(profile, "maven")));
        return profile;
    }

    @ExceptionHandler(ProfileManagement.InvalidUsernameException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid username")
    public void handleDataFormatException(ProfileManagement.InvalidUsernameException ex, HttpServletResponse response) {

    }

}
