package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @auther kuikui
 * @create 2020-10-30 15:34
 */
@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端
@MapperScan(basePackages = {"com.changgou.goods.dao"}) //开启通用Mapper的包扫描
public class GoodsApplication {
    public static void main(String[] args) {
            SpringApplication.run(GoodsApplication.class,args);
        }

    /***
     * IdWorker
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }
}
