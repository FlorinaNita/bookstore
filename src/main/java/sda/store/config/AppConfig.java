package sda.store.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("sda.store")
@EntityScan("sda.store.entities")
@EnableJpaRepositories("sda.store.repositories")
public class AppConfig {
}
