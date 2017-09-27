package nl.zzpmatcher.profile.business

import nl.zzpmatcher.profile.controller.ProfileRepository
import nl.zzpmatcher.utils.Validator.validate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody

class ProfileManagement(private val profileRepository: ProfileRepository) {

    fun updateProfile(updateProfileCommand: UpdateProfileCommand): Profile {
        validate(isUsernameEqualToLoggedOnUser(updateProfileCommand), { throw InvalidUsernameException() })

        val profile = ProfileBuilder.build(updateProfileCommand)

        profileRepository.save(profile)

        return profile
    }

    val profile: Profile
        get() {
            val username = SecurityContextHolder.getContext().authentication.name

            return profileRepository.findOne(username)
        }

    private fun isUsernameEqualToLoggedOnUser(@RequestBody updateProfileCommand: UpdateProfileCommand): Boolean {
        return updateProfileCommand.username == SecurityContextHolder.getContext().authentication.name
    }

    inner class InvalidUsernameException : RuntimeException("Invalid username")
}
