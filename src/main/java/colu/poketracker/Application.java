package colu.poketracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import colu.poketracker.repository.PokemonRepository;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = PokemonRepository.class)
@EnableWebMvc
@Configuration
@ComponentScan
public class Application {
	
	// It is unlikely that you will be able to use Spring Data Rest to complete the 
	// assignment due to differences in the responses provided by Spring Data Rest 
	// when adding new videos, etc.

	// The app now requires that you pass the location of the keystore and
	// the password for your private key that you would like to setup HTTPS
	// with. In Eclipse, you can set these options by going to:
	//    1. Run->Run Configurations
	//    2. Under Java Applications, select your run configuration for this app
	//    3. Open the Arguments tab
	//    4. In VM Arguments, provide the following information to use the
	//       default keystore provided with the sample code:
	//
	//       -Dkeystore.file=src/main/resources/private/keystore -Dkeystore.pass=changeit
	//
	//    5. Note, this keystore is highly insecure! If you want more securtiy, you 
	//       should obtain a real SSL certificate:
	//
	//       http://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html
	//
	// Tell Spring to launch our app!
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
