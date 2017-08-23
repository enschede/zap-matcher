package nl.zzpmatcher

import nl.zzpmatcher.userlogon.business.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ZzpMatcherApplication @Autowired
constructor(private val userRepository: UserRepository) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg strings: String) {
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ZzpMatcherApplication::class.java, *args)
        }
    }
}
