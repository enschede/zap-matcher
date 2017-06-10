package nl.zzpmatcher.business;

import static nl.zzpmatcher.business.utils.Validator.validate;

public class UserManagement {

    private final UserRepository userRepository;

    public UserManagement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRegisterCommand userRegisterCommand) {
        validate(isPasswordNonEmpty(userRegisterCommand), PasswordMandatoryException::new);
        validate(arePasswordsEqual(userRegisterCommand), PasswordShouldBeEqual::new);
        validate(isEmailAddressUnique(userRegisterCommand.getEmailaddress()), DuplicateEmailAddressException::new);

        return userRepository.save(userRegisterCommand.createUser());
    }

    private boolean isPasswordNonEmpty(UserRegisterCommand userRegisterCommand) {
        return userRegisterCommand.getPassword() != null && userRegisterCommand.getPassword().length() > 0;
    }

    private boolean arePasswordsEqual(UserRegisterCommand userRegisterCommand) {
        return userRegisterCommand.getPassword().equals(userRegisterCommand.getPassword2());
    }

    private boolean isEmailAddressUnique(String emailAddress) {
        return userRepository.findUserByEmailaddress(emailAddress) == null;
    }

    public class DuplicateEmailAddressException extends RuntimeException {
        DuplicateEmailAddressException() {
            super("DuplicateEmailAddressException");
        }
    }

    class PasswordMandatoryException extends RuntimeException {
        PasswordMandatoryException() {
            super("PasswordMandatoryException");
        }
    }

    class PasswordShouldBeEqual extends RuntimeException {
        PasswordShouldBeEqual() {
            super("PasswordShouldBeEqual");
        }
    }
}
