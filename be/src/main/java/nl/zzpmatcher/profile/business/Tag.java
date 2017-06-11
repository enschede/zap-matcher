package nl.zzpmatcher.profile.business;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Tag {
    @Id
    private String id;
    @ManyToOne
    private Profile profile;

    public static Tag of(Profile profile, String id) {
        final Tag tag = new Tag();
        tag.setId(id);
        tag.setProfile(profile);

        return tag;
    }
}
