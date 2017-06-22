package nl.zzpmatcher.profile.controller;

import nl.zzpmatcher.profile.business.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, String> {
}
