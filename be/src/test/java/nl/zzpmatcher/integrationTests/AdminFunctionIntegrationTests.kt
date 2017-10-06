package nl.zzpmatcher.integrationTests

import nl.zzpmatcher.userlogon.business.UserLoginCommand
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

import java.util.ArrayList
import java.util.LinkedHashMap

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminFunctionIntegrationTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @Test
    fun successfulLoginAsAdmin() {
        val objectResponseEntity = login("admin@zzpmatcher.nl", "hallo")

        assertThat<String>(objectResponseEntity.body.emailaddress, equalTo("admin@zzpmatcher.nl"))
        assertThat(objectResponseEntity.statusCode.value(), equalTo(200))
    }

    @Test
    fun failedLoginAsAdmin() {
        val objectResponseEntity = login("admin@zzpmatcher.nl", "wrongpassword")

        assertThat(objectResponseEntity.statusCode.value(), equalTo(404))
    }

    @Test
    fun successfulFetchOfUsers() {
        val loginResponseResponseEntity = login("admin@zzpmatcher.nl", "hallo")
        val cookie = getCookieFromResponse(loginResponseResponseEntity)

        val userList = getUserlist(cookie)

        assertThat(userList.statusCodeValue, equalTo(200))
        assertTrue(userList.body["content"] is ArrayList<*>)
    }

    private fun getUserlist(cookie: String): ResponseEntity<LinkedHashMap<*, *>> {
        val requestHeaders = HttpHeaders()
        requestHeaders.add("Cookie", cookie)
        val requestEntity = HttpEntity(null, requestHeaders)

        return testRestTemplate!!.exchange(
                "http://localhost:$port/admin/user", HttpMethod.GET, requestEntity, LinkedHashMap::class.java)
    }

    private fun getCookieFromResponse(loginResponseResponseEntity: ResponseEntity<LoginResponse>): String {
        return loginResponseResponseEntity.headers["Set-Cookie"]!!.get(0)
    }


    fun login(username: String, password: String): ResponseEntity<LoginResponse> {
        return this.testRestTemplate!!.postForEntity(
                "http://localhost:$port/public/login",
                buidUserLoginCommand(username, password),
                LoginResponse::class.java)
    }

    private fun buidUserLoginCommand(username: String, password: String): UserLoginCommand {
        val userLoginCommand = UserLoginCommand()

        userLoginCommand.username = username
        userLoginCommand.password = password

        return userLoginCommand
    }

    class LoginResponse {
        val emailaddress: String? = null
    }
}
