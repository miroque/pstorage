package ru.miroque.pstorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class PstorageApplication {
    @Value(value = "${tz}")
    private String tz;

    public static void main(String[] args) {
        SpringApplication.run(PstorageApplication.class, args);
    }

    @PostConstruct
    public void init() {

//        String [] ss=TimeZone.getAvailableIDs();
//        for (int i=0; i<ss.length; i++){
//            System.out.println(" = " + ss[i]);
//        }

//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Krasnoyarsk"));   // It will set UTC timezone
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Vladivostok"));   // It will set UTC timezone

//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
//        TimeZone.setDefault(TimeZone.getTimeZone("Japan"));   // It will set UTC timezone
        TimeZone.setDefault(TimeZone.getTimeZone(tz));   // It will set UTC timezone
        System.out.println("Spring boot application running in UTC timezone :" + new Date());   // It will print UTC timezone
    }
}
