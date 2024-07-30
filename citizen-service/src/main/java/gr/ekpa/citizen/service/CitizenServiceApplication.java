package gr.ekpa.citizen.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = "gr.ekpa.citizen")
@EntityScan(basePackages = "gr.ekpa.citizen.domain")
@EnableJpaRepositories(basePackages = "gr.ekpa.citizen.service")
public class CitizenServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CitizenServiceApplication.class, args);
    }
}