package com.codebrewers.onlinebookstore;

import com.codebrewers.onlinebookstore.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileProperties.class})
public class OnlinebookstoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(OnlinebookstoreApplication.class, args);
    }

}
