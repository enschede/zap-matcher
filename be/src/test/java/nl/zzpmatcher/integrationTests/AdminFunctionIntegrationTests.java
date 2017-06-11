package nl.zzpmatcher.integrationTests;

import nl.zzpmatcher.userLogon.business.UserLoginCommand;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminFunctionIntegrationTests {

	@LocalServerPort
	private int port = 0;

	@Autowired
	private TestRestTemplate testRestTemplate ;

	@Test
	public void successfulLoginAsAdmin() {
		final ResponseEntity<LoginResponse> objectResponseEntity = login("admin@zzpmatcher.nl", "test");

		assertThat(objectResponseEntity.getBody().getEmailaddress(), equalTo("admin@zzpmatcher.nl"));
		assertThat(objectResponseEntity.getStatusCode().value(), equalTo(200));
	}

	@Test
	public void failedLoginAsAdmin() {
		final ResponseEntity<LoginResponse> objectResponseEntity = login("admin@zzpmatcher.nl", "wrongpassword");

		assertThat(objectResponseEntity.getStatusCode().value(), equalTo(404));
	}

	@Test
	public void successfulFetchOfUsers() {
		final ResponseEntity<LoginResponse> loginResponseResponseEntity = login("admin@zzpmatcher.nl", "test");
		final String cookie = getCookieFromResponse(loginResponseResponseEntity);

		ResponseEntity<LinkedHashMap> userList = getUserlist(cookie);

		assertThat(userList.getStatusCodeValue(), equalTo(200));
		assertThat(userList.getBody().get("content"), CoreMatchers.instanceOf(ArrayList.class));
	}

	private ResponseEntity<LinkedHashMap> getUserlist(String cookie) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", cookie);
		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

		return testRestTemplate.exchange(
				"http://localhost:" + port + "/admin/user", HttpMethod.GET, requestEntity, LinkedHashMap.class);
	}

	private String getCookieFromResponse(ResponseEntity<LoginResponse> loginResponseResponseEntity) {
		return loginResponseResponseEntity.getHeaders().get("Set-Cookie").get(0);
	}


	public ResponseEntity<LoginResponse> login(String username, String password) {
		return this.testRestTemplate.postForEntity(
                "http://localhost:" + port + "/public/login",
                buidUserLoginCommand(username, password),
                LoginResponse.class);
	}

	private UserLoginCommand buidUserLoginCommand(String username, String password) {
		UserLoginCommand userLoginCommand = new UserLoginCommand();

		userLoginCommand.setUsername(username);
		userLoginCommand.setPassword(password);

		return userLoginCommand;
	}

	public static class LoginResponse {
		private String emailaddress;

		public String getEmailaddress() {
			return emailaddress;
		}
	}
}
