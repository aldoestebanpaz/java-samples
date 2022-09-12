package com.aldo.samples.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("STARTING THE APPLICATION");
        new SpringApplicationBuilder(Application.class)
            .web(WebApplicationType.NONE) // it is a console app, not a web application.
            .run(args);
        LOGGER.info("APPLICATION FINISHED");
    }

    @Bean
    public CommandLineRunner program() throws Exception {
        // CommandLineRunner#run method
        return args -> {
            LOGGER.info("Hello World");
        };
    }

}
