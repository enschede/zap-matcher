package nl.zzpmatcher;

import nl.zzpmatcher.business.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZzpMatcherApplication implements CommandLineRunner{

	private final UserRepository userRepository;

	@Autowired
	public ZzpMatcherApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ZzpMatcherApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
	}
}
