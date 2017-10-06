package nl.zzpmatcher.profile.controller

import nl.zzpmatcher.profile.business.Profile
import nl.zzpmatcher.profile.business.ProfileManagement
import nl.zzpmatcher.profile.business.UpdateProfileCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse

@RestController
class ProfileController @Autowired
constructor(profileRepository: ProfileRepository) {

    private val profileManagement: ProfileManagement

    init {
        profileManagement = ProfileManagement(profileRepository)
    }

    @PostMapping("/user/profile")
    fun postProfile(@RequestBody updateProfileCommand: UpdateProfileCommand): HttpEntity<ProfileProjection> {

        val profile = profileManagement.updateProfile(updateProfileCommand)

        return ResponseEntity.ok(ProfileProjection.of(profile))
    }

    val profile: HttpEntity<ProfileProjection>
        @GetMapping("/user/profile")
        get() {

            val profile = profileManagement.profile

            return ResponseEntity.ok(ProfileProjection.of(profile))
        }

    @ExceptionHandler(ProfileManagement.InvalidUsernameException::class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid username")
    fun handleDataFormatException(ex: ProfileManagement.InvalidUsernameException, response: HttpServletResponse) {

    }

}
