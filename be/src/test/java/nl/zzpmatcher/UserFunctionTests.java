package nl.zzpmatcher;

import org.hamcrest.CoreMatchers;
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
public class UserFunctionTests {

    @LocalServerPort
    private int port = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void successfulRegisterNewuser() {
        final ResponseEntity<AdminFunctionTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

        assertThat(registerResponse.getStatusCodeValue(), equalTo(200));
    }

    @Test
    public void doubleRegisterNewuser() {
        final ResponseEntity<AdminFunctionTests.LoginResponse> registerResponse1 = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc1@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionTests.LoginResponse> registerResponse2 = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc1@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

        assertThat(registerResponse2.getStatusCodeValue(), equalTo(500));
    }

    @Test
    public void successfulLoginAsUser() {
        final ResponseEntity<AdminFunctionTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionTests.LoginResponse> loginResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

        assertThat(loginResponse.getStatusCodeValue(), equalTo(200));
    }

    @Test
    public void fetchingUserlistFails() {
        final ResponseEntity<AdminFunctionTests.LoginResponse> registerResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/createUser",
                buildUserCreateCommand("marc2@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

        final ResponseEntity<AdminFunctionTests.LoginResponse> loginResponse = this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand("marc2@marc.nl", "guest"),
                AdminFunctionTests.LoginResponse.class);

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

    private UserCreateCommand buildUserCreateCommand(String username, String password) {
        UserCreateCommand userCreateCommand = new UserCreateCommand();

        userCreateCommand.setEmailaddress(username);
        userCreateCommand.setPassword(password);
        userCreateCommand.setPassword2(password);

        return userCreateCommand;
    }

    private String getCookieFromResponse(ResponseEntity<AdminFunctionTests.LoginResponse> loginResponseResponseEntity) {
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