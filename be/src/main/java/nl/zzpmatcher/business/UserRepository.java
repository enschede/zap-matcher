package nl.zzpmatcher.business;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findUserByEmailaddress(String emailAddress);

}
