package nl.zzpmatcher.userlogon.controller

import nl.zzpmatcher.userlogon.business.UserLoginCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class SecurityController @Autowired
constructor(private val authenticationManager: AuthenticationManager) {

    @PostMapping("/public/login")
    fun login(@RequestBody userLoginCommand: UserLoginCommand): HttpEntity<*> {
        val token = UsernamePasswordAuthenticationToken(userLoginCommand.username, userLoginCommand.password)

        try {
            val auth = authenticationManager.authenticate(token)
            SecurityContextHolder.getContext().authentication = auth

            val resource = Resource(UserEmailaddressOnlyProjection(SecurityContextHolder.getContext().authentication.name))
            UserResourceAssembler.addLogoutLinks(resource)
            return ResponseEntity.ok(resource)
        } catch (ex: BadCredentialsException) {
            return ResponseEntity.notFound().build<Any>()
        }

    }

    @GetMapping("/user/logout")
    fun logout(): HttpEntity<*> {
        SecurityContextHolder.clearContext()

        val resource = Resource("")
        UserResourceAssembler.addLoginLink(resource)
        return ResponseEntity.ok(resource)
    }

}
