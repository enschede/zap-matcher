package nl.zzpmatcher.controllers.user;

public class UserEmailaddressOnlyProjection {

    private String emailaddress;

    public UserEmailaddressOnlyProjection(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }
}
