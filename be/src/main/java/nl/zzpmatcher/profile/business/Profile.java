package nl.zzpmatcher.profile.business;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Profile {
    @Id
    private String id;
    @OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Tag> tags;

}
