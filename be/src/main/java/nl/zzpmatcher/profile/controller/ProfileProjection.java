package nl.zzpmatcher.profile.controller;

import nl.zzpmatcher.profile.business.Profile;
import nl.zzpmatcher.profile.business.Tag;

public class ProfileProjection {
    private final String username;
    private final String[] tags;

    private ProfileProjection(String username, String[] tags) {
        this.username = username;
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public String[] getTags() {
        return tags;
    }

    static ProfileProjection of(Profile profile) {
        if(profile==null)
            return null;

        final String[] tags = profile.getTags().stream().map(Tag::getId).toArray(String[]::new);

        return new ProfileProjection(profile.getId(), tags);
    }
}
