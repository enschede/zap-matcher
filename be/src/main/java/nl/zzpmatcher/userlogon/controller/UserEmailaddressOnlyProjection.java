package nl.zzpmatcher.userlogon.controller;

public class UserEmailaddressOnlyProjection {

    private final String emailaddress;

    UserEmailaddressOnlyProjection(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getEmailaddress() {
        return emailaddress;
    }
}
