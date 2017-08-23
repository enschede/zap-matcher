package nl.zzpmatcher.profile.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tag {
    @Id
    private String id;
    @ManyToOne
    @JsonIgnore
    private Profile profile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public static Tag of(Profile profile, String id) {
        final Tag tag = new Tag();
        tag.setId(id);
        tag.setProfile(profile);

        return tag;
    }
}
