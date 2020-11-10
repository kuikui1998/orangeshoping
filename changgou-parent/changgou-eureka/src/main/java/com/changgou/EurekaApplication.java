package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @auther kuikui
 * @create 2020-10-30 13:57
 */
@SpringBootApplication
@EnableEurekaServer //开启Eureka服务
public class EurekaApplication {
    public static void main(String[] args) {
            SpringApplication.run(EurekaApplication.class,args);
        }
}
