package nl.zzpmatcher.profile.business;

import lombok.Data;

@Data(staticConstructor = "of")
public class UpdateProfileCommand {
    private final String username;
    private final String[] tags;
}
