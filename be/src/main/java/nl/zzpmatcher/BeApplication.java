package nl.zzpmatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeApplication implements CommandLineRunner{

	private final UserRepository userRepository;

	@Autowired
	public BeApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BeApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
	}
}
