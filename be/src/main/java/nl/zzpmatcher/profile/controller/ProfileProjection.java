package nl.zzpmatcher.profile.controller;

import lombok.Data;
import nl.zzpmatcher.profile.business.Profile;

@Data
public class ProfileProjection {
    private final String username;
    private final String[] tags;

    public static ProfileProjection of(Profile profile) {
        final String[] tags = profile.getTags().stream().map(tag -> tag.getId()).toArray(String[]::new);

        return new ProfileProjection(profile.getId(), tags);
    }
}
