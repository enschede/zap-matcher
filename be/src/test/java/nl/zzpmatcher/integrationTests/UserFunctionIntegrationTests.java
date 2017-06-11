package nl.zzpmatcher.integrationTests;

import nl.zzpmatcher.profile.business.UpdateProfileCommand;
import nl.zzpmatcher.userLogon.business.UserManagementTest;
import nl.zzpmatcher.userLogon.business.UserRegisterCommand;
import nl.zzpmatcher.userLogon.business.UserLoginCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserFunctionIntegrationTests {

    @LocalServerPort
    private int port = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void successfulRegisterNewuser() {
        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        assertThat(registerResponse.getStatusCodeValue(), equalTo(200));
    }

    @Test
    public void failOnDoubleRegisterNewuser() {
        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse1 = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc1@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse2 = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc1@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        assertThat(registerResponse2.getStatusCodeValue(), equalTo(409));
    }

    @Test
    public void successfulLoginAsUser() {
        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> loginResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        assertThat(loginResponse.getStatusCodeValue(), equalTo(200));
    }

    @Test
    public void fetchingUserlistFailsForInsufficientAuthorization() {
        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> loginResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final String cookie = getCookieFromResponse(loginResponse);

        ResponseEntity<LinkedHashMap> userList = getUserlist(cookie);

        assertThat(userList.getStatusCodeValue(), equalTo(403));
    }

    @Test
    public void successfulUserProfileCreation() {
        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> loginResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final String cookie = getCookieFromResponse(loginResponse);

        final ResponseEntity<LinkedHashMap> exchange =
                executeUpdateProfileCommand(cookie, buildUpdateProfileCommand("marc2@marc.nl", "java", "spring"));

        assertThat(exchange.getStatusCodeValue(), equalTo(200));
    }

    @Test
    public void failUserProfileCreationOnWrongUsername() {
        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> loginResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionIntegrationTests.LoginResponse.class);

        final String cookie = getCookieFromResponse(loginResponse);

        final ResponseEntity<LinkedHashMap> exchange =
                executeUpdateProfileCommand(cookie, buildUpdateProfileCommand("marc3@marc.nl", "java", "spring"));

        assertThat(exchange.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
    }

    private UpdateProfileCommand buildUpdateProfileCommand(String username, String... tags) {
        return UpdateProfileCommand.of(username, tags);
    }

    private UserLoginCommand buidUserLoginCommand(String username, String password) {
        UserLoginCommand userLoginCommand = new UserLoginCommand();

        userLoginCommand.setUsername(username);
        userLoginCommand.setPassword(password);

        return userLoginCommand;
    }

    private UserRegisterCommand buildUserCreateCommand(String username, String password) {
        return UserManagementTest.UserRegisterCommandBuilder.build(username, password, password);
    }

    private String getCookieFromResponse(ResponseEntity<AdminFunctionIntegrationTests.LoginResponse> loginResponseResponseEntity) {
        return loginResponseResponseEntity.getHeaders().get("Set-Cookie").get(0);
    }

    private ResponseEntity<LinkedHashMap> getUserlist(String cookie) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", cookie);
        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

        return testRestTemplate.exchange(
                "http://localhost:" + port + "/admin/controller", HttpMethod.GET, requestEntity, LinkedHashMap.class);
    }

    private ResponseEntity<LinkedHashMap> executeUpdateProfileCommand(String cookie, UpdateProfileCommand updateProfileCommand) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", cookie);
        HttpEntity requestEntity = new HttpEntity(updateProfileCommand, requestHeaders);

        return testRestTemplate.exchange(
                "http://localhost:" + port + "/user/profile", HttpMethod.POST, requestEntity, LinkedHashMap.class);
    }

}