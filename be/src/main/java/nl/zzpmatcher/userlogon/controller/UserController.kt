package nl.zzpmatcher.userlogon.controller

import nl.zzpmatcher.userlogon.business.User
import nl.zzpmatcher.userlogon.business.UserManagement
import nl.zzpmatcher.userlogon.business.UserRegisterCommand
import nl.zzpmatcher.userlogon.business.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse

@RestController
class UserController @Autowired
constructor(private val userRepository: UserRepository, private val userResourceAssembler: UserResourceAssembler) {

    private val userManagement: UserManagement

    init {

        this.userManagement = UserManagement(userRepository)
    }

    @ExceptionHandler(UserManagement.DuplicateEmailAddressException::class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Double!!")
    fun handleDataFormatException(ex: UserManagement.DuplicateEmailAddressException, response: HttpServletResponse) {

    }

    @PostMapping("/public/createUser")
    fun postCreateUser(@RequestBody userRegisterCommand: UserRegisterCommand): Resource<*> {

        val createdUser = userManagement.createUser(userRegisterCommand)

        return userResourceAssembler.toEmailAddressOnlyProjection(createdUser)
    }

    val user: HttpEntity<Resources<User>>
        @GetMapping("/admin/user")
        get() {

            val users = userRepository.findAll()

            val userResource = userResourceAssembler.toResources(users)
            return ResponseEntity.ok(userResource)
        }

    // TODO: voor debug acties, daarna verwijderen
    val allProfiles: Iterable<User>
        @GetMapping("/public/all")
        get() = userRepository.findAll()
}
