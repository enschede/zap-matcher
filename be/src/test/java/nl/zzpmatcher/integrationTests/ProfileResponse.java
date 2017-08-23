package nl.zzpmatcher.integrationTests;

public class ProfileResponse {
        private String username;
        private String[] tags;

        public ProfileResponse() {
        }

        public ProfileResponse(String username, String[] tags) {
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
}
