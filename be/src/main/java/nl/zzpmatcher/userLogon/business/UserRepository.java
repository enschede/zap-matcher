package nl.zzpmatcher.userLogon.business;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmailaddress(String emailAddress);

}
