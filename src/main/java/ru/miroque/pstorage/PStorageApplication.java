package ru.miroque.pstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class PStorageApplication {

    public static void main(String[] args) {
        System.out.println("Spring boot application running in timezone :" + new Date());
        SpringApplication.run(PStorageApplication.class, args);
    }
}
