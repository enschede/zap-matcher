package nl.zzpmatcher.integrationTests;

import nl.zzpmatcher.business.UserManagementTest;
import nl.zzpmatcher.business.UserRegisterCommand;
import nl.zzpmatcher.business.UserLoginCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    public void doubleRegisterNewuser() {
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
    public void fetchingUserlistFails() {
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
                "http://localhost:" + port + "/admin/user", HttpMethod.GET, requestEntity, LinkedHashMap.class);
    }

}