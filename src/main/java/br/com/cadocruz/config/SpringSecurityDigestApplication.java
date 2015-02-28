package br.com.cadocruz.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.cadocruz")
public class SpringSecurityDigestApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDigestApplication.class, args);
    }
}
