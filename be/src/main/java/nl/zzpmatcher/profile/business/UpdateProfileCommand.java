package nl.zzpmatcher.profile.business;

public class UpdateProfileCommand {
    private String username;
    private String[] tags;

    public UpdateProfileCommand() {
    }

    private UpdateProfileCommand(String username, String[] tags) {
        this.username = username;
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public static UpdateProfileCommand of(String username, String[] tags) {
        return new UpdateProfileCommand(username, tags);
    }
}
