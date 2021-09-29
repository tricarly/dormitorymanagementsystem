package com.suyi.dms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.suyi.dms.dao")
public class DormitorymanagementsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormitorymanagementsystemApplication.class, args);
    }

}
