package nl.zzpmatcher.userlogon.security

import nl.zzpmatcher.userlogon.business.User
import nl.zzpmatcher.userlogon.business.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import java.util.Arrays
import java.util.stream.Collectors
import kotlin.streams.toList

@Component
class SecurityBeans @Autowired
constructor(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService(): UserDetailsService {

        return UserDetailsService { s ->
            val user = userRepository.findUserByEmailaddress(s)

            object : UserDetails {
                override fun getAuthorities(): Collection<GrantedAuthority> {

                    return Arrays
                            .stream(individualRoles)
                            .map { GrantedAuthority({it}) }
                            .toList()
                }

//                private fun mapToGrantedAuthority(role: String): GrantedAuthority {
//                    return { role }
//                }

                private val individualRoles: Array<String>
                    get() = user.roles!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                override fun getPassword(): String? {
                    return user?.password
                }

                override fun getUsername(): String? {
                    return user.emailaddress
                }

                override fun isAccountNonExpired(): Boolean {
                    return true
                }

                override fun isAccountNonLocked(): Boolean {
                    return true
                }

                override fun isCredentialsNonExpired(): Boolean {
                    return true
                }

                override fun isEnabled(): Boolean {
                    return true
                }
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
