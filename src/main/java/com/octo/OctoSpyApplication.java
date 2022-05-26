package com.octo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OctoSpyApplication {

    /**
     * Methods to start the projects.
     *
     * @param args Options.
     */
    public static void main(final String[] args) {
        SpringApplication.run(OctoSpyApplication.class, args);
    }

    /**
     * Bean for password encoder.
     *
     * @return Instance of password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
