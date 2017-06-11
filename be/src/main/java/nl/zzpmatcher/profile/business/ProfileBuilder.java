package nl.zzpmatcher.profile.business;

import nl.zzpmatcher.profile.business.Profile;
import nl.zzpmatcher.profile.business.Tag;
import nl.zzpmatcher.profile.business.UpdateProfileCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileBuilder {

    public static Profile build(UpdateProfileCommand updateProfileCommand) {

        final Profile profile = new Profile();
        profile.setId(updateProfileCommand.getUsername());

        final List<Tag> tagList = Arrays.stream(updateProfileCommand.getTags()).map(tag -> Tag.of(profile, tag)).collect(Collectors.toList());

        profile.setTags(tagList);

        return profile;
    }
}
