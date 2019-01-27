package com.sfs.pbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PbserverApplication {

    //@PostConstruct
    //void setDefaultTimezone() {
      //  TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
//    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    //}
    public static void main(String[] args) {
        SpringApplication.run(PbserverApplication.class, args);
    }

}

