package nl.zzpmatcher;

import static nl.zzpmatcher.Validator.validate;

public class UserCreateCommand {

    private String emailaddress;
    private String password;
    private String password2;

    public UserCreateCommand() {
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public User createUser() {

        validate(password != null && password.length() > 0, () -> new RuntimeException("Password is mandatory"));
        validate(password.equals(password2), () -> new RuntimeException("Passwords should be equal"));

        return User.create(emailaddress, password);
    }
}
