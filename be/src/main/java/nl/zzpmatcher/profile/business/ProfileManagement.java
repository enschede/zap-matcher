package nl.zzpmatcher.profile.business;

import nl.zzpmatcher.profile.controller.ProfileRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;

import static nl.zzpmatcher.utils.Validator.validate;

public class ProfileManagement {

    private final ProfileRepository profileRepository;

    public ProfileManagement(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile updateProfile(UpdateProfileCommand updateProfileCommand) {
        validate(isUsernameEqualToLoggedOnUser(updateProfileCommand), InvalidUsernameException::new);

        final Profile profile = ProfileBuilder.build(updateProfileCommand);

        profileRepository.save(profile);

        return profile;
    }

    public Profile getProfile() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return profileRepository.findOne(username);
    }

    private boolean isUsernameEqualToLoggedOnUser(@RequestBody UpdateProfileCommand updateProfileCommand) {
        return updateProfileCommand.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public class InvalidUsernameException extends RuntimeException {
        public InvalidUsernameException() {
            super("Invalid username");
        }
    }
}
