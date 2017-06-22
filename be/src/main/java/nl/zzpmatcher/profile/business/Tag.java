package nl.zzpmatcher.profile.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@ToString(exclude = "profile")
public class Tag {
    @Id
    private String id;
    @ManyToOne
    @JsonIgnore
    private Profile profile;

    public static Tag of(Profile profile, String id) {
        final Tag tag = new Tag();
        tag.setId(id);
        tag.setProfile(profile);

        return tag;
    }
}
