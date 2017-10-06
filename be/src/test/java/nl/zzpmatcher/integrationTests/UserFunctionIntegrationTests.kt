package nl.zzpmatcher.integrationTests

import nl.zzpmatcher.profile.business.UpdateProfileCommand
import nl.zzpmatcher.profile.controller.ProfileRepository
import nl.zzpmatcher.userlogon.business.UserLoginCommand
import nl.zzpmatcher.userlogon.business.UserManagementTest
import nl.zzpmatcher.userlogon.business.UserRegisterCommand
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner

import java.util.LinkedHashMap

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserFunctionIntegrationTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @Test
    fun successfulRegisterNewuser() {
        val registerResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        assertThat(registerResponse.statusCodeValue, equalTo(200))
    }

    @Test
    fun failOnDoubleRegisterNewuser() {
        val registerResponse1 = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc1@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val registerResponse2 = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc1@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        assertThat(registerResponse2.statusCodeValue, equalTo(409))
    }

    @Test
    fun successfulLoginAsUser() {
        val registerResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val loginResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        assertThat(loginResponse.statusCodeValue, equalTo(200))
    }

    @Test
    fun fetchingUserlistFailsForInsufficientAuthorization() {
        val registerResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val loginResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val cookie = getCookieFromResponse(loginResponse)

        val userList = getUserlist(cookie)

        assertThat(userList.statusCodeValue, equalTo(403))
    }

    @Test
    fun successfulUserProfileUpdate() {
        val registerResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val loginResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val cookie = getCookieFromResponse(loginResponse)

        val exchange = executeUpdateProfileCommand(cookie, buildUpdateProfileCommand("marc2@marc.nl", "java", "spring"))

        assertThat(exchange.statusCodeValue, equalTo(200))
        assertThat<String>(exchange.body.username, equalTo("marc2@marc.nl"))
        assertThat(exchange.body.tags!![0], equalTo("java"))
        assertThat(exchange.body.tags!![1], equalTo("spring"))
    }

    @Test
    fun successfulUserProfileRead() {
        val registerResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val loginResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val cookie = getCookieFromResponse(loginResponse)

        val profileUpdateResponse = executeUpdateProfileCommand(cookie, buildUpdateProfileCommand("marc2@marc.nl", "java", "spring"))

        val profileReadResponse = executeReadProfileCommand(cookie)

        assertThat(profileUpdateResponse.statusCodeValue, equalTo(200))
        assertThat<String>(profileReadResponse.body.username, equalTo("marc2@marc.nl"))
        assertThat(profileReadResponse.body.tags!![0], equalTo("java"))
        assertThat(profileReadResponse.body.tags!![1], equalTo("spring"))
    }

    @Test
    fun failUserProfileCreationOnWrongUsername() {
        val registerResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val loginResponse = this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse::class.java)

        val cookie = getCookieFromResponse(loginResponse)

        val exchange = executeUpdateProfileCommand(cookie, buildUpdateProfileCommand("marc3@marc.nl", "java", "spring"))

        assertThat(exchange.statusCode, equalTo(HttpStatus.NOT_ACCEPTABLE))
    }

    private fun buildUpdateProfileCommand(username: String, vararg tags: String): UpdateProfileCommand {
        return UpdateProfileCommand.of(username, tags.toList().toTypedArray())
    }

    private fun buidUserLoginCommand(username: String, password: String): UserLoginCommand {
        val userLoginCommand = UserLoginCommand()

        userLoginCommand.username = username
        userLoginCommand.password = password

        return userLoginCommand
    }

    private fun buildUserCreateCommand(username: String, password: String): UserRegisterCommand {
        return UserManagementTest.UserRegisterCommandBuilder.build(username, password, password)
    }

    private fun getCookieFromResponse(loginResponseResponseEntity: ResponseEntity<AdminFunctionIntegrationTests.LoginResponse>): String {
        return loginResponseResponseEntity.headers["Set-Cookie"]!!.get(0)
    }

    private fun getUserlist(cookie: String): ResponseEntity<LinkedHashMap<*, *>> {
        val requestHeaders = HttpHeaders()
        requestHeaders.add("Cookie", cookie)
        val requestEntity = HttpEntity(null, requestHeaders)

        return testRestTemplate!!.exchange(
                "http://localhost:$port/admin/controller", HttpMethod.GET, requestEntity, LinkedHashMap::class.java)
    }

    private fun executeUpdateProfileCommand(cookie: String, updateProfileCommand: UpdateProfileCommand): ResponseEntity<ProfileResponse> {
        val requestHeaders = HttpHeaders()
        requestHeaders.add("Cookie", cookie)
        val requestEntity = HttpEntity(updateProfileCommand, requestHeaders)

        return testRestTemplate!!.exchange(
                "http://localhost:$port/user/profile", HttpMethod.POST, requestEntity, ProfileResponse::class.java)
    }

    private fun executeReadProfileCommand(cookie: String): ResponseEntity<ProfileResponse> {
        val requestHeaders = HttpHeaders()
        requestHeaders.add("Cookie", cookie)
        val requestEntity = HttpEntity(null, requestHeaders)

        return testRestTemplate!!.exchange(
                "http://localhost:$port/user/profile", HttpMethod.GET, requestEntity, ProfileResponse::class.java)
    }


    private class ProfileResponse {
        var username: String? = null
        var tags: Array<String>? = null
    }
}